package com.api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class TestApi {

    //������Ŀ�Ļ�����û������
    @Test
    public void test(){
        System.out.println("hello world!");
    }

    //����get����
    @Test
    public void test1(){
        given().
                queryParam("phone","12345").
                queryParam("password","12345").
        when().
                get("https://httpbin.org/get").
        then().
                log().body();
    }

    //����post���󣨱�������
    @Test
    public void test2(){
        given().
                contentType("application/x-www-form-urlencoded").
                body("hello=123&world=456").
        when().
                post("https://httpbin.org/post").
        then().
                log().body();
    }
    //����post����json������
    @Test
    public void test3(){
        given().
                contentType("application/json").
                body("{\"mobilephone\":\"12333\",\"pwd\":\"123\"}").
        when().
                post("https://httpbin.org/post").
        then().
                log().body();
    }
    //�ļ��ϴ�
    @Test
    public void test4(){
        Response res = given().contentType("multipart/form-data").
                multiPart(new File("C:\\Users\\80423\\Desktop\\123.txt")).
        when().
                post("https://httpbin.org/post").
        then().log().body().extract().response();
      /*  System.out.println(res.time());
        System.out.println(res.statusCode());*/
        System.out.println(res.jsonPath().get("headers.Content-Type").toString());

    }
}
