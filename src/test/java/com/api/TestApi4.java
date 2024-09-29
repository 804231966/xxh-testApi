package com.api;

import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class TestApi4 {
    private static String loginToken;

    String filePath = "C:\\Users\\80423\\Pictures\\1.png";
    byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

    public TestApi4() throws IOException {
    }

    @BeforeTest
    public void loginTest(){
        Response res =
                given().
                        contentType("application/json").
                        body("{\"username\":\"test\",\"password\":\"49ba59abbe56e057\"}").
                when().
                        post("http://47.96.40.143:5000/login\n").
                then().
                        log().body().extract().response();

        loginToken=res.jsonPath().get("token");
        System.out.println("token:"+loginToken);
    }
    @Test(enabled = false)
    public void selectTest(){
        Response res =
                given().header("authorization","Bearer "+ loginToken).
                        when().
                        get("http://47.96.40.143:5000/data?current=1&size=1\n").
                        then().
                        log().body().extract().response();

        loginToken=res.jsonPath().get("token");
        System.out.println("token:"+loginToken);
    }

    @Test
    public void addTest(){
        Response res =
                given().
                        header("Authorization","Bearer "+ loginToken).
                        contentType("multipart/form-data").
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
                        log().body().extract().response();

    }
}
