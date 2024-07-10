import POJO_Ecommerce.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class L14_LoginAndGenerateToken_69_70_71_72 {

    String token;
    String userId;
    String productId;
    String orderId;

    @Test
    public void p1_loginAndGenerateToken()
    {
        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                setContentType(ContentType.JSON).build();
        LoginPayload loginPayload = new LoginPayload();
        loginPayload.setUserEmail("alisharose@gmail.com");
        loginPayload.setUserPassword("Roserose7");
        RequestSpecification request = given().log().all().spec(requestSpec).body(loginPayload);

        LoginResponse loginResponse = request.when().post("api/ecom/auth/login").
                then().log().all().extract().as(LoginResponse.class);
        token = loginResponse.getToken();
        userId = loginResponse.getUserId();

    }
    @Test
    public void p2_addProductAndGetProductId()
    {
        RequestSpecification requestSpec1 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).build();
        LinkedHashMap<String,String> formParamsMap = new LinkedHashMap<>();
        formParamsMap.put("productName","BambooPlate");
        formParamsMap.put("productAddedBy",userId);
        formParamsMap.put("productCategory","Cutlery");
        formParamsMap.put("productSubCategory","Biodegradable Plates");
        formParamsMap.put("productDescription","Affordable and high qualityBiodegradable Plates");
        formParamsMap.put("productPrice","1");
        formParamsMap.put("productFor","Restaurant");

        RequestSpecification request1 = given().log().all().spec(requestSpec1).formParams(formParamsMap).
                multiPart("productImage",new File("/Users/aish/Documents/RestAPIAutomation/APIAutomation/src/main/resources/BambooPlate.png"));

        String response1 = request1.when().post("api/ecom/product/add-product").
                then().log().body().extract().response().asString();
        JsonPath js = new JsonPath(response1);
        productId = js.getString("productId");

    }
    @Test
    public void p3_createOrder()
    {

        Level2_CreateOrderDetails orderDetails = new Level2_CreateOrderDetails();
        orderDetails.setProductOrderedId(productId);
        orderDetails.setCountry("India");
        List<Level2_CreateOrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);
        Level1_CreateOrder orders = new Level1_CreateOrder();
        orders.setOrders(orderDetailsList);

        RequestSpecification requestSpec2 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("Authorization",token).setContentType(ContentType.JSON).build();

        RequestSpecification request2 = given().spec(requestSpec2).body(orders);

        String response = request2.when().post("api/ecom/order/create-order").
                then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(response);
        orderId = js.get("orders[0]");


    }
    @Test
    public void p4_deleteProduct()
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().header("Authorization",token).pathParam("productid",productId)
                .when().delete("api/ecom/product/delete-product/{productid}")
                .then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void p5_getOrderDetails()
    {
        RequestSpecification requestSpec3 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).addQueryParam("id",orderId).build();

        RequestSpecification request3 = given().log().all().spec(requestSpec3);
        Level1_getOrderDetails orderDetails = request3.when().get("api/ecom/order/get-orders-details").
                then().log().all().extract().as(Level1_getOrderDetails.class);
        System.out.println("Order deets msg: "+orderDetails.getMessage());
    }
    @Test
    public void p6_deleteOrder()
    {
        RequestSpecification requestSpec4 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).addPathParam("id",orderId).build();
        RequestSpecification request4 = given().log().all().spec(requestSpec4);
        String response = request4.when().delete("api/ecom/order/delete-order/{id}").
                then().extract().response().asString();
        JsonPath js = new JsonPath(response);
        System.out.println("order delete msg: "+js.getString("message"));
    }
}
