package org.example.cdklambda.cdk;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class FileProcessingLambda implements RequestHandler<S3Event, String> {

    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("FileProcessingLambda is running");
        return "Finished running FileProcessingLambda";
    }
}