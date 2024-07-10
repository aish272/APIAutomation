import POJO_Serialiazation_MapsRequestPayload.Level1_addPlace_62;
import POJO_Serialiazation_MapsRequestPayload.Level2_location_62;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class L12_POJOImplementSerialization63 {

    @Test
    public void addPlaceByConvertingJavaObjToRequestPayloadJson()
    {
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
        addPlaceJavaObj.setTypes(Arrays.asList("HQ","Office"));
        addPlaceJavaObj.setLocation(location);

        /**Use java object to send request body */
        RestAssured.baseURI="https://rahulshettyacademy.com";

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(addPlaceJavaObj)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200);

    }

}
