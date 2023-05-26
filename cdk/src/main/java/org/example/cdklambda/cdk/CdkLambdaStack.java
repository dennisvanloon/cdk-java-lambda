package org.example.cdklambda.cdk;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

public class CdkLambdaStack extends Stack {

    private static final String targetBucket = "s3-notifications-lambda-bucket";

    public CdkLambdaStack(final App parent, final String id) {
        this(parent, id, null);
    }

    public CdkLambdaStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Bucket bucket = Bucket.Builder.create(this,targetBucket).build();

        Function fileProcessingLambda = Function.Builder.create(this, "fileProcessingLambda")
                .runtime(Runtime.JAVA_11)
                .functionName("fileProcessingLambda")
                .timeout(Duration.seconds(30))
                .memorySize(1024)
                .code(Code.fromAsset("../file-processing-lambda/target/file-processing-lambda.jar"))
                .handler("org.example.cdklambda.cdk.FileProcessingLambda::handleRequest")
                .build();
    }
}
