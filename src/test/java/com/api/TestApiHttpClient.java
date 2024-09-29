package com.api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class TestApiHttpClient {

    //在测试套件前，执行1次
    @BeforeSuite
    public void bsuite()
    {
        System.out.println(1);
    }
    //在测试套件后，执行1次
    @AfterSuite
    public void bsuite2()
    {
        System.out.println(2);

    }

    //在每个测试case前，执行1次
    @BeforeTest
    public void btest()
    {
        System.out.println(3);
    }

    //在每个测试case后，执行1次
    @AfterTest
    public void atest()
    {
        System.out.println(4);
    }



    @Test(enabled = false)
    public void test1() throws IOException {
        //get请求

        //1、请求地址
        String url = "https://httpbin.org/get";

        //2、请求方法
        HttpGet get = new HttpGet(url);

        //3、准备一个发包的客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //4、通过客户端发包
        CloseableHttpResponse response = httpclient.execute(get);

        //5、状态行 getStatusLine
        String StatusLine = response.getStatusLine().toString();
        int StatusCode = response.getStatusLine().getStatusCode();
        String StatusPhrase = response.getStatusLine().getReasonPhrase();
        String ProtocolVersion = response.getStatusLine().getProtocolVersion().toString();

        //Assert.assertTrue(StatusCode == 200);
        System.out.println("状态code正确");
        Assert.assertEquals(StatusPhrase, "OK");

        //6、响应头
        //System.out.println(response.getAllHeaders());

        //7、响应体
        // System.out.println(response.getEntity());
    }

}
