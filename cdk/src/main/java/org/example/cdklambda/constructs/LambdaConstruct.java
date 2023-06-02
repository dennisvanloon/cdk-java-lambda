package org.example.cdklambda.constructs;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.iam.PolicyDocument;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

import java.util.Collections;
import java.util.List;

import static software.amazon.awscdk.Duration.seconds;
import static software.amazon.awscdk.services.ec2.SubnetType.PUBLIC;
import static software.amazon.awscdk.services.iam.Effect.ALLOW;
import static software.amazon.awscdk.services.lambda.Code.fromAsset;
import static software.amazon.awscdk.services.lambda.Runtime.JAVA_11;

public class LambdaConstruct extends Construct {

    public LambdaConstruct(@NotNull Construct parent, @NotNull String name, Bucket bucket, Vpc vpc) {
        super(parent, name);

        PolicyStatement statement1 = PolicyStatement.Builder.create()
                .effect(ALLOW)
                .actions(List.of("s3:GetObject","s3:PutObject"))
                .resources(List.of("arn:aws:s3:::"+bucket.getBucketName()+"/*"))
                .build();

        PolicyStatement statement2 = PolicyStatement.Builder.create()
                .effect(ALLOW)
                .actions(List.of("logs:CreateLogGroup","logs:CreateLogStream","logs:PutLogEvents"))
                .resources(List.of("arn:aws:logs:*:*:*"))
                .build();

        PolicyStatement statement3 = PolicyStatement.Builder.create()
                .effect(ALLOW)
                .actions(List.of("ec2:CreateNetworkInterface", "ec2:DescribeNetworkInterfaces", "ec2:DeleteNetworkInterface"))
                .resources(List.of("*"))
                .build();

        PolicyDocument policyDocument = PolicyDocument.Builder.create()
                .statements(List.of(statement1,statement2,statement3)).build();

        Role lambdaRole = Role.Builder.create(this,"LambdaIamRole")
                .inlinePolicies(Collections.singletonMap("key", policyDocument))
                .assumedBy(new ServicePrincipal("lambda.amazonaws.com")).build();

        Function.Builder.create(this, "FileProcessingLambda")
                .runtime(JAVA_11)
                .timeout(seconds(30))
                .memorySize(1024)
                .code(fromAsset("../file-processing-lambda/target/file-processing-lambda.jar"))
                .handler("org.example.cdklambda.cdk.FileProcessingLambda::handleRequest")
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder().subnetType(PUBLIC).build())
                .allowPublicSubnet(true)
                .role(lambdaRole)
                .build();
    }

}
