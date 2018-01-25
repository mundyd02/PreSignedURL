

package uk.co.bbc.gdpr;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import uk.co.bbc.gdpr.utils.UnsignedUrl;


import java.util.ArrayList;
import java.util.List;


public class Main {

    private static String bucketName = "scv-int-seg-runner-sql";
    private static String objectKey  =  "dpm.sql";

    public static void main(String[] args) {
	// write your code here
            System.out.println("hello there");

        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

        UnsignedUrl unsignedUrl = new UnsignedUrl(s3client);

        List<String> urlList;
        List<String> fileList = new ArrayList<String>();
        fileList.add("dpm.sql");
        fileList.add("testUnloadSeg1.sql");

        urlList = unsignedUrl.CreateUnsignedUrlList(bucketName, fileList);
        unsignedUrl.ShowUrlListLog(urlList);

        /*try {
            System.out.println("Generating pre-signed URL.");
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 1000 * 60 * 60; // Add 1 hour.
            expiration.setTime(milliSeconds);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);

            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed URL = " + url.toString());
        } catch (AmazonServiceException exception) {
            System.out.println("Caught an AmazonServiceException, " +
                    "which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message: " + exception.getMessage());
            System.out.println("HTTP  Code: "    + exception.getStatusCode());
            System.out.println("AWS Error Code:" + exception.getErrorCode());
            System.out.println("Error Type:    " + exception.getErrorType());
            System.out.println("Request ID:    " + exception.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, " +
                    "which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }*/
    }
}
