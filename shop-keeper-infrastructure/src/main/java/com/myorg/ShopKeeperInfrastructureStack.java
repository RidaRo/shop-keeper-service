package com.myorg;

import software.amazon.awscdk.CfnParameter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.constructs.Construct;

public class ShopKeeperInfrastructureStack extends Stack {
    private final ISecret postgresSecret;

    public ShopKeeperInfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public ShopKeeperInfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        var postgresDatabaseName = CfnParameter.Builder.create(this, "postgresDatabase")
                .description("Postgres database name")
                .defaultValue("item")
                .build();

        var postgresUsername = CfnParameter.Builder.create(this, "postgresUsername")
                .description("Postgres admin user name")
                .defaultValue("keeper")
                .build();

        postgresSecret = DatabaseSecret.Builder.create(this, "PostgresCredentials")
                .username(postgresUsername.getValueAsString())
                .build();

        Vpc vpc = Vpc.Builder.create(this, "MyVpc")
                .maxAzs(3)  // Default is all AZs in region
                .build();

        Cluster cluster = Cluster.Builder.create(this, "MyCluster")
                .vpc(vpc).build();

        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService.Builder.create(this, "MyFargateService")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage
                                        .fromEcrRepository(Repository
                                                        .fromRepositoryName(this, "EcrRepository", "shop-keeper-service"),
                                                "a45fe36c236779e5eb12b5391e11d1b836dbfc95"))
                                .containerPort(8080)
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        var database = DatabaseInstance.Builder.create(this, "Postgres")
                .engine(DatabaseInstanceEngine.postgres(
                                PostgresInstanceEngineProps.builder()
                                        .version(PostgresEngineVersion.VER_13_4)
                                        .build()
                        )
                )
                .vpc(vpc)
                //todo ask about error when choosing SubnetType.PRIVATE_ISOLATED
                .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PUBLIC).build())
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .databaseName(postgresDatabaseName.getValueAsString())
                .credentials(Credentials.fromSecret(postgresSecret))
                .build();

        database.getConnections().allowFromAnyIpv4(Port.tcp(5432), "Allow connections to the database");
    }
}
