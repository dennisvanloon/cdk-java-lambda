package org.example.cdklambda.cdk;

import org.example.cdklambda.constructs.LambdaConstruct;
import org.example.cdklambda.constructs.RdsPostgresConstruct;
import org.example.cdklambda.constructs.S3Construct;
import org.example.cdklambda.constructs.VpcConstruct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class CdkLambdaStack extends Stack {

    public CdkLambdaStack(final Construct parent, final String id, final StackProps stackProps) {
        super(parent, id, stackProps);

        VpcConstruct vpcConstruct = new VpcConstruct(this, this.getStackName() + ".VpcConstruct");
        RdsPostgresConstruct rdsPostgresConstruct =
                new RdsPostgresConstruct(this, this.getStackName() + ".RdsPostgresConstruct", vpcConstruct.getVpc());
        S3Construct s3Construct = new S3Construct(this, this.getStackName() + ".S3Construct");
        new LambdaConstruct(this, this.getStackName() + ".LambdaConstruct", s3Construct.getBucket(), vpcConstruct.getVpc());
    }
}
