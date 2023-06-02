package org.example.cdklambda.cdk;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public final class CdkLambdaApp {
    public static void main(final String[] args) {

        StackProps stackProps = makeStackProps();

        App app = new App();
        new CdkLambdaStack(app, "LambdaPackagingStack", stackProps);
        app.synth();
    }

    static StackProps makeStackProps() {
        String account = System.getenv("CDK_DEFAULT_ACCOUNT");
        String region = System.getenv("CDK_DEFAULT_REGION");

        requireNonEmpty(account, "accountId unknown");
        requireNonEmpty(region, "region unknown");

        Environment environment = Environment.builder()
                .account(account)
                .region(region)
                .build();

        return StackProps.builder()
                .env(environment)
                .build();
    }

    private static void requireNonEmpty(String string, String message) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

}
