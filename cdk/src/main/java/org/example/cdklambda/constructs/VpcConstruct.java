package org.example.cdklambda.constructs;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import java.util.ArrayList;
import java.util.List;

public class VpcConstruct extends Construct {

    private final Vpc vpc;

    public VpcConstruct(@NotNull Construct parent, @NotNull String name) {
        super(parent, name);

        List<SubnetConfiguration> subnetConfigurations = new ArrayList<>();
        subnetConfigurations.add(SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .name("public ")
                .cidrMask(24)
                .build());

        vpc = Vpc.Builder.create(this, "vpc")
                .cidr("10.0.0.0/16")
                .maxAzs(2)
                .subnetConfiguration(subnetConfigurations)
                .build();
    }

    public Vpc getVpc() {
        return vpc;
    }
}
