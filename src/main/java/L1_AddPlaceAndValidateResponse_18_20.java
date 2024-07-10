import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import inputFiles.Payload;

public class L1_AddPlaceAndValidateResponse_18_20 {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";

        /******
         * given() : All input details
         * when() : resource and HTTP request type
         * then() : validate response
         * **********/
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.getPayPostload())
        .when().post("maps/api/place/add/json")
        .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)");


    }
}
