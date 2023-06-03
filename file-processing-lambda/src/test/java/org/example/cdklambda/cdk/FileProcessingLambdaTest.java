package org.example.cdklambda.cdk;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.serialization.PojoSerializer;
import com.amazonaws.services.lambda.runtime.serialization.events.LambdaEventSerializers;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileProcessingLambdaTest {

    @Mock
    Context contextMock;

    @Mock
    LambdaLogger loggerMock;

    @Mock
    AmazonS3Client amazonS3ClientMock;

    private final FileProcessingLambda fileProcessingLambda = new FileProcessingLambda();

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        Field field = FileProcessingLambda.class.getDeclaredField("amazonS3Client");
        field.setAccessible(true);
        field.set(fileProcessingLambda, amazonS3ClientMock);

        when(contextMock.getLogger()).thenReturn(loggerMock);
        when(amazonS3ClientMock.getObject("mybucket", "HappyFace.jpg")).thenReturn(null);
    }

    @Test
    public void test() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("s3-put-object.json");
        PojoSerializer<S3Event> s3EventSerializer = LambdaEventSerializers.serializerFor(S3Event.class, ClassLoader.getSystemClassLoader());
        S3Event event = s3EventSerializer.fromJson(inputStream);

        String result = fileProcessingLambda.handleRequest(event, contextMock);

        assertEquals("Finished running FileProcessingLambda", result);

        verify(amazonS3ClientMock).getObject("mybucket", "HappyFace.jpg");


//        String expected = "This is a dummy mocked content";
//        InputStream inputStream = IOUtils.toInputStream(expected);
//        S3ObjectInputStream s3Input = new S3ObjectInputStream(inputStream,null);
//        S3Object s3Object = mock(S3Object.class);
//        AmazonS3Client amazonS3Client = mock(AmazonS3Client.class);
//        S3AttachmentsService service = new S3AttachmentsService(amazonS3Client);
//        when(amazonS3Client.getObject(any(GetObjectRequest.class)).thenReturn(s3Object);
//        when(s3Object.getObjectContent).thenReturn(s3Input);
    }
}
