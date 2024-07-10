import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;

public class L10_EndToEndOAuthTest_52 {

    String accessToken;

    @Test
    public void p1_getAccessTokenUsingClientCredentialGrant()
    {
        RestAssured.baseURI ="https://rahulshettyacademy.com/oauthapi";
        LinkedHashMap <String, String> formParams = new LinkedHashMap<>();
        formParams.put("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret","erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type","client_credentials");
        formParams.put("scope","trust");
        String response = given().formParams(formParams).
                when().log().all().post("oauth2/resourceOwner/token").
                then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(response);
        accessToken =js.getString("access_token");

    }
    @Test
    public void p2_useAccessTokenToRetrieveCourseDetails()
    {
        RestAssured.baseURI ="https://rahulshettyacademy.com/oauthapi";
        String response = given().queryParam("access_token",accessToken).
                when().log().all().get("/getCourseDetails").
                then().extract().response().asString();
        JsonPath js = new JsonPath(response);
        int size =js.getInt("courses.size()");
        System.out.println("Course size: "+size);



    }
}
