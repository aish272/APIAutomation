import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class L8_SendStaticPayloadThroughExternalJson_39 {

    @Test
    public void addPlaceUsingPayloadFetchedFromExternalJsonFile() throws IOException {
        RestAssured.baseURI="https://rahulshettyacademy.com";

        //Passing json payload through external json file. Valid only for static json
        given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Path.of("/Users/aish/Documents/APIAutomation/src/main/resources/addplaceJson.json"))))
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)");


    }
}
