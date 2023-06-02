package org.example.cdklambda.constructs;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

public class S3Construct extends Construct {

    private static final String BUCKET_NAME = "s3-notifications-lambda-bucket";

    private final Bucket bucket;

    public S3Construct(@NotNull Construct parent, @NotNull String name) {
        super(parent, name);

        bucket = Bucket.Builder.create(this, BUCKET_NAME).build();
    }

    public Bucket getBucket() {
        return bucket;
    }
}
