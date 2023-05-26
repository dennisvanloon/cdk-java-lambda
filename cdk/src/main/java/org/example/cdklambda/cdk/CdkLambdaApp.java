package org.example.cdklambda.cdk;

import software.amazon.awscdk.App;

public final class CdkLambdaApp {
    public static void main(final String[] args) {
        App app = new App();
        new CdkLambdaStack(app, "LambdaPackagingStack");
        app.synth();
    }
}
