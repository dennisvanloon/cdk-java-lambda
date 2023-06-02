package org.example.cdklambda.constructs;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.List;

import static software.amazon.awscdk.RemovalPolicy.DESTROY;
import static software.amazon.awscdk.services.ec2.InstanceClass.BURSTABLE3;
import static software.amazon.awscdk.services.ec2.InstanceSize.MICRO;
import static software.amazon.awscdk.services.ec2.Peer.anyIpv4;
import static software.amazon.awscdk.services.ec2.Port.tcp;
import static software.amazon.awscdk.services.ec2.SubnetType.PUBLIC;

public class RdsPostgresConstruct extends Construct {

    private final DatabaseInstance databaseInstance;

    public RdsPostgresConstruct(@NotNull Construct parent, @NotNull String name, Vpc vpc) {
        super(parent, name);

        final IInstanceEngine instanceEngine = DatabaseInstanceEngine.postgres(
                PostgresInstanceEngineProps.builder()
                        .version(PostgresEngineVersion.VER_13_6)
                        .build()
        );

        SecurityGroupProps securityGroupProps = SecurityGroupProps.builder()
                .vpc(vpc)
                .build();
        SecurityGroup securityGroup = new SecurityGroup(this, "securityGroup", securityGroupProps);
        securityGroup.addIngressRule(anyIpv4(), tcp(5432));

        this.databaseInstance = DatabaseInstance.Builder.create(this, "database")
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder().subnetType(PUBLIC).build())
                .instanceType(InstanceType.of(BURSTABLE3, MICRO))
                .engine(instanceEngine)
                .removalPolicy(DESTROY)
                .securityGroups(List.of(securityGroup))
                .build();
    }

    public DatabaseInstance getDatabaseInstance() {
        return databaseInstance;
    }
}