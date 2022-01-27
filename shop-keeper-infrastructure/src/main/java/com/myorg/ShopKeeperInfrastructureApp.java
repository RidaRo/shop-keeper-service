package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class ShopKeeperInfrastructureApp {
    public static void main(final String[] args) {
        App app = new App();

        new ShopKeeperInfrastructureStack(app, "ShopKeeperInfrastructureStack", StackProps.builder()
                .build());

        app.synth();
    }
}

