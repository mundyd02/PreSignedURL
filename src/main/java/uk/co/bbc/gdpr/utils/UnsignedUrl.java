package uk.co.bbc.gdpr.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UnsignedUrl {

    private static final Logger logger = LoggerFactory.getLogger(UnsignedUrl.class);
    private AmazonS3 s3client;



    public UnsignedUrl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public List<String> CreateUnsignedUrlList(String bucketName, List<String> fileKeyList) {
        return CreateUnsignedUrlList(bucketName, fileKeyList, new DateExpiration());
    }

    public List<String> CreateUnsignedUrlList(String bucketName, List<String> fileKeyList, DateExpiration dateExpiration) {
        List<String> unsignedUrlList = new ArrayList<String>();

        for (String fileKey : fileKeyList) {
            URL unsignedUrlFile = GetURL(bucketName, fileKey, dateExpiration);
            unsignedUrlList.add(unsignedUrlFile.toString());
        }

        return unsignedUrlList;
    }

    public void ShowUrlListLog(List<String> urlList) {
        for (String fileUrl : urlList) {
            logger.info("File URL: " + fileUrl) ;
        }
    }

    public URL GetURL(String bucketName, String fileKey, DateExpiration expiration) {

        URL url = null;

        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration.getExpirationDate());

            url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

            return url;

        } catch (Exception ex) {
            logger.error("GetUrl failed ", ex);
        }
        return url;
    }
}
