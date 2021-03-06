package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnParameter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.Secret;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.sns.Topic;
import software.constructs.Construct;

import java.util.Map;

public class ShopKeeperInfrastructureStack extends Stack {

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

        var imageTag = CfnParameter.Builder.create(this, "imageTag")
                .type("String")
                .description("The tag of ecr container")
                .defaultValue("default image tag")
                .build();

        var postgresSecret = DatabaseSecret.Builder.create(this, "PostgresCredentials")
                .username(postgresUsername.getValueAsString())
                .build();

        var vpc = Vpc.Builder.create(this, "MyVpc")
                .maxAzs(2)  // Default is all AZs in region
                .subnetConfiguration(Vpc.DEFAULT_SUBNETS_NO_NAT)
                .build();

        Cluster cluster = Cluster.Builder.create(this, "MyCluster")
                .vpc(vpc).build();

        // RDS
        var database = DatabaseInstance.Builder.create(this, "Postgres")
                .engine(DatabaseInstanceEngine.postgres(
                                PostgresInstanceEngineProps.builder()
                                        .version(PostgresEngineVersion.VER_13_4)
                                        .build()
                        )
                )
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build())
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .databaseName(postgresDatabaseName.getValueAsString())
                .credentials(Credentials.fromSecret(postgresSecret))
                .build();

        database.getConnections().allowFromAnyIpv4(Port.tcp(5432), "Allow connections to the database");

        // Fargate
        var keeperService = ApplicationLoadBalancedFargateService.Builder.create(this, "MyFargateService")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage
                                        .fromEcrRepository(Repository
                                                        .fromRepositoryName(this, "EcrRepository", "shop-keeper-service"),
                                                imageTag.getValueAsString())
                                )
                                .containerPort(8080)
                                .environment(Map.of
                                        ("POSTGRES_HOST", database.getDbInstanceEndpointAddress(),
                                                "POSTGRES_PORT", database.getDbInstanceEndpointPort(),
                                                "POSTGRES_DATABASE", postgresDatabaseName.getValueAsString()))
                                .secrets(Map.of
                                        ("POSTGRES_PASSWORD", Secret.fromSecretsManager(postgresSecret, "password"),
                                                "POSTGRES_USER", Secret.fromSecretsManager(postgresSecret, "username")))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        // Health check for target group using Spring Boot actuator
        keeperService.getTargetGroup().configureHealthCheck(
                HealthCheck.builder()
                        .path("/actuator/health")
                        .healthyThresholdCount(2)
                        .unhealthyThresholdCount(5)
                        .build());

        // SNS
        var topic = Topic.Builder.create(this, "ItemsUpdates")
                .fifo(true)
                .topicName("item-updates.fifo")
                .build();

        CfnOutput.Builder.create(this, "ItemsUpdateTopic")
                .description("Item update topic")
                .value(topic.getTopicArn())
                .build();
    }
}
