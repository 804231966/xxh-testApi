package com.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestApi5 {
    private static String Token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcyNzU3NjU0NiwianRpIjoiNjFjMDRkMGItMjFiYi00YTUyLWEyYjAtYWVhOWMxNWZmMzU3IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6InRlc3QiLCJuYmYiOjE3Mjc1NzY1NDYsImV4cCI6MTcyNzU3ODM0Nn0.WsEBBFFFuBhHyqCItUe1p1D2MgxKUeIA-GJzzpGp_iE"
            ;


    @Test(enabled = false)
    public void loginTest(){

        Response res = given().
                    contentType("application/json").
                    body("{\"username\":\"test\",\"password\":\"49ba59abbe56e057\"}").
                        when().
                                post("http://47.96.40.143:5000/login\n").
                        then().
                                log().body().extract().response();

        System.out.println(res.jsonPath().get("code").toString());
        Token = res.jsonPath().get("token").toString();
        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");

    }
    @Test
    public void selectTest(){

        Response res = given().
                header("Authorization","Bearer "+Token).
                when().
                get("http://47.96.40.143:5000/data?current=1&size=1\n").
                then().
                log().body().extract().response();

        List<String> l1 = res.jsonPath().getList("data._name");

        System.out.println(l1.get(0));
        Assert.assertEquals(l1.get(0),"xxh");


    }
    @Test
    public void addTest() throws IOException {
        String filePath = "C:\\Users\\80423\\Pictures\\1.png";
        byte[] FileContent = Files.readAllBytes(Paths.get(filePath));

        Response res = given().
                                contentType("multipart/form-data").
                                header("Authorization","Bearer "+Token).
                                formParam("_name","1").
                                formParam("age","1").
                                formParam("city","1").
                                formParam("job","1").
                                formParam("dataFrom","1").
                                formParam("history","1").
                                formParam("face","1").
                                formParam("bad","1").
                                formParam("good" ,"1").
                                formParam("hobby","1").
                                formParam("remark","1").
                                multiPart("picture","1.png",FileContent,"image/png").
                when().
                        post("http://47.96.40.143:5000/add\n").
                then().
                log().body().extract().response();

        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");


    }





}
