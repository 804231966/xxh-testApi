package com.api;


import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Paths;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestApi2 {
    private static String token;

    String filePath = "C:\\Users\\80423\\Pictures\\1.png";
    byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

    public TestApi2() throws IOException {
    }

    //登录,获取token
    @BeforeClass
    public void loginTest(){
        Response res =
        given().
                contentType("application/json").
                body("{\"username\":\"test\",\"password\":\"49ba59abbe56e057\"}").
                when().
                post("http://47.96.40.143:5000/login\n").
                then().
                log().body().extract().response();
        Assert.assertEquals("200", res.jsonPath().get("code").toString());
        token  = res.jsonPath().get("token");
    }
    @Test
    public void test2()  {

        Response res =
                    given().
                            header("Authorization", "Bearer " + token).
                            contentType("multipart/form-data").
                           // multiPart("data",formData,"text/plain").
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
                            multiPart("picture","1.png",fileContent,"image/png").
                    when().
                            post("http://47.96.40.143:5000/add\n").
                    then().
                            log().all().extract().response();
        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");
        Assert.assertEquals(res.jsonPath().get("msg").toString(),"成功");

    }
    //查询
    @Test
    public void test3(){
        Response res =
                        given().
                                header("Authorization", "Bearer " + token).
                        when().
                                get("http://47.96.40.143:5000/data?current=1&size=12\n").
                        then().
                                log().body().extract().response();

        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");
    }
    @Test
    public void test4(int current,int size){
        Response res =
                given().
                        header("Authorization","Bearer "+token).
                when().
                        get("http://47.96.40.143:5000/data?current="+current+"&size="+size).
                then().
                        log().body().extract().response();
        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");
    }
    @Test
    public void test5(){
        Response res =
                given().
                        header("Authorization","Bearer "+token).
                        contentType("multipart/form-data").
                        multiPart("picture","1.png",fileContent,"image/png").
                when().
                        post("http://47.96.40.143:5000/add\n").
                then().
                        log().body().extract().response();
    }

}


//ssh-keygen -t rsa -C "804231966@qq.com"
//SXwj+J4Jv1KN85xibgXwIXX7Qr4UOGyeVA8EWNSF8UE