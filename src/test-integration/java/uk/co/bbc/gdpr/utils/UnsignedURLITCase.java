

package uk.co.bbc.gdpr.utils;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.bbc.gdpr.utils.UnsignedUrl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class UnsignedURLITCase {

    private static String bucketName = "scv-int-seg-runner-sql";
    private static String objectKey  =  "dpm.sql";

    @Test
    public void UnsignedURLITCase() {
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
        assert(urlList.size() == 2);

    }
}
