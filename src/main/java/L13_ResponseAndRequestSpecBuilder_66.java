import POJO_Serialiazation_MapsRequestPayload.Level1_addPlace_62;
import POJO_Serialiazation_MapsRequestPayload.Level2_location_62;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class L13_ResponseAndRequestSpecBuilder_66 {

    @Test
    public void addPlaceByConvertingJavaObjToRequestPayloadJson() {
        Level1_addPlace_62 addPlaceJavaObj = new Level1_addPlace_62();
        Level2_location_62 location = new Level2_location_62();
        location.setLat(27.055189);
        location.setLng(79.918083);
        addPlaceJavaObj.setAccuracy(100);
        addPlaceJavaObj.setName("Venuvan, Bharat");
        addPlaceJavaObj.setPhone_number("983 893 3333");
        addPlaceJavaObj.setAddress("East, UP, India");
        addPlaceJavaObj.setWebsite("https://google.com/MumVenuvan");
        addPlaceJavaObj.setLanguage("Hindi");
        addPlaceJavaObj.setTypes(Arrays.asList("HQ", "Office"));
        addPlaceJavaObj.setLocation(location);

        /* **********Build request spec */
        RequestSpecification requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).setBody(addPlaceJavaObj).build();


        // pass the request spec builder and store in a variable.
        RequestSpecification request = given().log().all().spec(requestSpecBuilder);

        /* **********Build response spec */
        ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //Call when() on the request spec obj and use response spec to validate response
        Response response = request.when().post("maps/api/place/add/json")
                .then().spec(responseSpecBuilder).extract().response();
        System.out.println("__________________________Response__________________");
        System.out.println(response.asString());

    }

}
