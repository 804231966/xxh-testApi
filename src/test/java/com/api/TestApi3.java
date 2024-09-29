package com.api;


import io.restassured.response.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestApi3 {

    //设定一个全局变量存储token

    private static String token;

    //数据来源
    private static String EXCEL_PATH = "C:\\Users\\80423\\IdeaProjects\\api_frame\\testData\\testlogin.xls";
    @DataProvider(name = "loginData")
    public Object[][] getDataFromCSV() throws IOException{
        FileInputStream fis = new FileInputStream(new File(EXCEL_PATH));
        Workbook workbook = null;
        if (EXCEL_PATH.endsWith(".xls")){
            workbook = new HSSFWorkbook(fis);
        }
        else if(EXCEL_PATH.endsWith(".xlsx")){
            workbook = new XSSFWorkbook(fis);
        }
        fis.close();
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        List<Object[]> data = new ArrayList<>();
        for (int i = 1; i < rowCount ; i++) {
            Row row = sheet.getRow(i);
            Cell usernameCell = row.getCell(1);
            Cell passwordCell = row.getCell(2);

            String username = getStringValueFromCell(usernameCell);
            String password = getStringValueFromCell(passwordCell);
            data.add(new Object[]{username,password});
        }
        workbook.close();

        return data.toArray(new Object[data.size()][]);
    }

    private String getStringValueFromCell(Cell cell){
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return "ERROR";
            default:
                return "";
        }
    }

    //登录case
    @Test(dataProvider = "loginData")
    public void loginTest(String username,String password){
        System.out.println(username);
        System.out.println(password);

        Response res =
            given().
                    contentType("application/json").
                    body("{\"username\":\""+username +"\",\"password\":\""+ password +"\"}").
            when().
                    post("http://47.96.40.143:5000/login\n").
            then().
                    log().body().extract().response();

            Assert.assertEquals(res.jsonPath().get("code").toString(),"200");

            token = res.jsonPath().get("token");

    }
    @Test
    //查询信息
    public void selectTest(){

        Response res =
            given().
                    header("authorization","Bearer " + token).
            when().
                    get("http://47.96.40.143:5000/data?current=1&size=1\n").
            then().
                   log().body().extract().response();
        List<String> l1 = res.jsonPath().getList("data._name");
        Assert.assertEquals(res.jsonPath().get("code").toString(),"200");
        Assert.assertEquals(l1.get(0),"xxh");

        }

}
