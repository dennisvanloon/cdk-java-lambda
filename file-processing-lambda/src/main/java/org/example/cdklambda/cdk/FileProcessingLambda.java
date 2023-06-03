package org.example.cdklambda.cdk;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

public class FileProcessingLambda implements RequestHandler<S3Event, String> {

    private AmazonS3 amazonS3Client = new AmazonS3Client();

    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();

        if(s3Event.getRecords().get(0).getEventName().equals("ObjectCreated:Put")) {
            S3EventNotification.S3Entity s3Entity = s3Event.getRecords().get(0).getS3();
            S3Object xFile = amazonS3Client.getObject(s3Entity.getBucket().getName(), s3Entity.getObject().getKey());
//            List<String> lines = new BufferedReader(new InputStreamReader(xFile.getObjectContent(), UTF_8))
//                    .lines().collect(toList());
//
//            logger.log("Found object with " + lines.size() + " lines");
        }

//        try {
//            logger.log("Loading Driver class");
//            Class.forName("org.postgresql.Driver");
//            logger.log("Getting connection");
//            //GET CONNECTION INFO FROM ARNs in the ENV
//            String url = "";
//            String username = "";
//            String password = "";
//            Connection connection = DriverManager.getConnection(url, username, password);
//            context.getLogger().log("Completed getConnection call");
//            connection.close();
//            context.getLogger().log("Connection closed");
//        }
//        catch (Exception e)
//        {
//            context.getLogger().log("Exception:"+e.getMessage()+":"+e.getStackTrace());
//            String message = "Exception:"+e.getMessage()+":"+e.getStackTrace();
//        }
//        logger.log("FileProcessingLambda is running");
        return "Finished running FileProcessingLambda";
    }
}