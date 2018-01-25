package uk.co.bbc.gdpr.utils;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class UnsignedUrlTest {


    @Mock
    private AmazonS3 s3Client;

    @Mock
    private DateExpiration dateExpiration;

    private UnsignedUrl unsignedUrl;

    @Before
    public void setUp() {
        unsignedUrl = new UnsignedUrl(s3Client);
    }

    @Test
    public void UnsignedUrl_getUrl_returns_URL() {
        URL url = null;
        try {
            url = new URL("http://URL1");
        } catch (Exception ex) {
           System.out.println(ex);
        }
        when(s3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url);

        URL urlResult = unsignedUrl.GetURL("bucketName", "fileName", dateExpiration);
        assert(url.equals(urlResult));
    }

    @Test
    public void UnsignedUrl_CreateUnsignedURLList_returns_list_empty() {
        URL url1 = null;

        when(s3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url1);

        List<String> fileList = new ArrayList<String>();
        List<String> resultsList = unsignedUrl.CreateUnsignedUrlList("bucketName", fileList, dateExpiration);

        assertThat(resultsList, is(notNullValue()));
        assert(resultsList.size() == 0);

    }

    @Test
    public void UnsignedUrl_CreateUnsignedURLList_returns_list_one() {
        URL url1 = null;
        try {
            url1 = new URL("http://URL1");
        } catch (Exception ex) { }
        when(s3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url1);

        List<String> fileList = Arrays.asList("http://URL1");
        List<String> resultsList = unsignedUrl.CreateUnsignedUrlList("bucketName", fileList, dateExpiration);

        int count = 0;

        for(String url : resultsList) {
            count ++;
            assert(url.equals(String.format("http://URL%s" ,count)));
        }

        assert(count == 1);

    }

    @Test
    public void UnsignedUrl_CreateUnsignedURLList_returns_list_multiple() {
        URL url1 = null;
        URL url2 = null;
        try {
            url1 = new URL("http://URL1");
            url2 = new URL("http://URL2");
        } catch (Exception ex) { }
        when(s3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url1, url2);

        List<String> fileList = Arrays.asList("http://URL1", "http://URL2");
        List<String> resultsList = unsignedUrl.CreateUnsignedUrlList("bucketName", fileList, dateExpiration);

        int count = 0;

        for(String url : resultsList) {
            count ++;
            assert(url.equals(String.format("http://URL%s" ,count)));
        }

        assert(count == 2);

    }
}
