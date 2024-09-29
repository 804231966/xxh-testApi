package com.api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class TestApiHttpClient {

    //�ڲ����׼�ǰ��ִ��1��
    @BeforeSuite
    public void bsuite()
    {
        System.out.println(1);
    }
    //�ڲ����׼���ִ��1��
    @AfterSuite
    public void bsuite2()
    {
        System.out.println(2);

    }

    //��ÿ������caseǰ��ִ��1��
    @BeforeTest
    public void btest()
    {
        System.out.println(3);
    }

    //��ÿ������case��ִ��1��
    @AfterTest
    public void atest()
    {
        System.out.println(4);
    }



    @Test(enabled = false)
    public void test1() throws IOException {
        //get����

        //1�������ַ
        String url = "https://httpbin.org/get";

        //2�����󷽷�
        HttpGet get = new HttpGet(url);

        //3��׼��һ�������Ŀͻ���
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //4��ͨ���ͻ��˷���
        CloseableHttpResponse response = httpclient.execute(get);

        //5��״̬�� getStatusLine
        String StatusLine = response.getStatusLine().toString();
        int StatusCode = response.getStatusLine().getStatusCode();
        String StatusPhrase = response.getStatusLine().getReasonPhrase();
        String ProtocolVersion = response.getStatusLine().getProtocolVersion().toString();

        //Assert.assertTrue(StatusCode == 200);
        System.out.println("״̬code��ȷ");
        Assert.assertEquals(StatusPhrase, "OK");

        //6����Ӧͷ
        //System.out.println(response.getAllHeaders());

        //7����Ӧ��
        // System.out.println(response.getEntity());
    }

}
