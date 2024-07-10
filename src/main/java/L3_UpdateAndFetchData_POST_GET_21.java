import inputFiles.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class L3_UpdateAndFetchData_POST_GET_21 {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json").body(Payload.getPayPostload()).
                when().post("maps/api/place/add/json").then()
                .extract().response().asString(); //extracting json
        JsonPath jp = new JsonPath(addPlaceResponse);
        //parsing json
        String placeID = jp.getString("place_id");

        //Update address
        String newAddress = "Venice, Italy";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").
                body("{\n" +
                        "\"place_id\":\"ab40eef7c82435ae43ac2acd64cc6cb4\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").
                when().put("/maps/api/place/update/json").
                then().log().all().assertThat().statusCode(200).assertThat().body("msg",equalTo("Address successfully updated"));

        //Validate updated address using get
        HashMap <String,String> queryParam = new HashMap<>();
        queryParam.put("key", "qaclick123");
        queryParam.put("place_id", placeID);
        String getCallJsonResponse = given().log().all().queryParams(queryParam).when().get("maps/api/place/get/json").
                then().log().all().assertThat().statusCode(200).extract().asString();
        JsonPath getJsonResponse = new JsonPath(getCallJsonResponse);
        String fetchedUpdatedAddress = getJsonResponse.getString("address");
        Assert.assertEquals(fetchedUpdatedAddress,newAddress);


    }
}
