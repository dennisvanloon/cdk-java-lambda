package org.example.cdklambda.cdk;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

import java.sql.Connection;
import java.sql.DriverManager;

public class FileProcessingLambda implements RequestHandler<S3Event, String> {

    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();

        try {
            logger.log("Loading Driver class");
            Class.forName("org.postgresql.Driver");
            logger.log("Getting connection");
            //GET CONNECTION INFO FROM ARNs in the ENV
            String url = "";
            String username = "";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            context.getLogger().log("Completed getConnection call");
            connection.close();
            context.getLogger().log("Connection closed");
        }
        catch (Exception e)
        {
            context.getLogger().log("Exception:"+e.getMessage()+":"+e.getStackTrace());
            String message = "Exception:"+e.getMessage()+":"+e.getStackTrace();
        }
        logger.log("FileProcessingLambda is running");
        return "Finished running FileProcessingLambda";
    }
}