import POJO_Deserialiazation_OAuthResponsePayload.Level1_json_55;
import POJO_Deserialiazation_OAuthResponsePayload.Level3b_API_56;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class L11_POJOImplementDeserialization58_59 {

    @Test
    public void pojoImplementation() {
        //get access token by using client cred grant
        RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi";
        LinkedHashMap<String, String> formParams = new LinkedHashMap<>();
        formParams.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        formParams.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
        formParams.put("grant_type", "client_credentials");
        formParams.put("scope", "trust");
        String response = given().formParams(formParams).
                when().post("oauth2/resourceOwner/token").
                then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        //use access token to fetch info and store json response in a java object
        Level1_json_55 mainCourseObject = given().queryParam("access_token", accessToken).
                when().get("/getCourseDetails").
                then().log().all().extract().response().as(Level1_json_55.class);

        /** get basic details from java object **/
        System.out.println("linkedInURL = " + mainCourseObject.getLinkedIn());
        System.out.println("instructor = " + mainCourseObject.getInstructor());

        /* Number of courses in webautomation */
        System.out.println("Number of courses in webautomation = " + mainCourseObject.getCourses().getWebAutomation().size());

        /* Price of Rest assured api course */
        List<Level3b_API_56> apiCourseList = mainCourseObject.getCourses().getApi();
        for (Level3b_API_56 apiCourse : apiCourseList) {

            if (apiCourse.getCourseTitle().equals("Rest Assured Automation using Java")) {
                System.out.println("Price of Rest assured api course: " + apiCourse.getPrice());
                break;
            }

        }

    }
}

