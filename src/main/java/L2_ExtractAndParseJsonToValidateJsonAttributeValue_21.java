import inputFiles.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class L2_ExtractAndParseJsonToValidateJsonAttributeValue_21 {

    public static void main(String[] args)
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String addPlaceResponse = given().log().all().queryParam("key","qaclick123")
                                  .header("Content-Type","application/json").body(Payload.getPayPostload()).
                                  when().post("maps/api/place/add/json").then()
                .extract().response().asString(); //extracting json
        JsonPath jp = new JsonPath(addPlaceResponse);
        System.out.println("Extracted Json Response:"+addPlaceResponse);
        //parsing json
        System.out.println("Place id:"+jp.getString("place_id"));

    }
}
