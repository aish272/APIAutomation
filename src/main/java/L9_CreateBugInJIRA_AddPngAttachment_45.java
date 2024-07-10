import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class L9_CreateBugInJIRA_AddPngAttachment_45 {
    String issueID;
    File attachment = new File("/Users/aish/Documents/APIAutomation/src/main/resources/jiraAttach.png");

    @Test
    public void p001_createBug() throws IOException {
        RestAssured.baseURI ="https://aishwaryalearningapiautomation.atlassian.net";
        LinkedHashMap <String,String> headers = new LinkedHashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Basic bWlzaHJhLmFpc2h3YXJ5YTk2OUBnbWFpbC5jb206QVRBVFQzeEZmR0YwZlp2YzluS3V1V1plNXgtMTZvTUtYRC1mXy1TaTVEOHozN0RmS0dDRkUxUW10RlhZWmgxYnNySldTNEVUaTdhMWNkSzYxelluVG1EblBuV1lvZFJSYm9OVFhKdFluOGROdEdJNGVwbDMzQlV1WHNnTVhVWjdNdENvNGVIeHhoMzhETmo1YmRuV2x5LXpIdzNXY19hYTNTNkVqdnJRaGcxZjhJNnU4U1piN3ZBPTkxQjA1MzI5");
        String createBugResponse = given().log().all().headers(headers).
        body(new String(Files.readAllBytes(Path.of("/Users/aish/Documents/APIAutomation/src/main/resources/createBugJson.json"))))
                .when().post("/rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(createBugResponse);
        issueID = js.getString("id");
    }

    @Test
    public void p002_addAttachment()
    {
        RestAssured.baseURI ="https://aishwaryalearningapiautomation.atlassian.net";
        LinkedHashMap <String,String> headers = new LinkedHashMap<>();
        headers.put("X-Atlassian-Token","no-check");
        headers.put("Authorization","Basic bWlzaHJhLmFpc2h3YXJ5YTk2OUBnbWFpbC5jb206QVRBVFQzeEZmR0YwZlp2YzluS3V1V1plNXgtMTZvTUtYRC1mXy1TaTVEOHozN0RmS0dDRkUxUW10RlhZWmgxYnNySldTNEVUaTdhMWNkSzYxelluVG1EblBuV1lvZFJSYm9OVFhKdFluOGROdEdJNGVwbDMzQlV1WHNnTVhVWjdNdENvNGVIeHhoMzhETmo1YmRuV2x5LXpIdzNXY19hYTNTNkVqdnJRaGcxZjhJNnU4U1piN3ZBPTkxQjA1MzI5");
        given().log().all().headers(headers).pathParam("bugID",issueID).
                multiPart("file", attachment).
                when().post("rest/api/3/issue/{bugID}/attachments").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void p003_getIssueDetails()
    {
        RestAssured.baseURI ="https://aishwaryalearningapiautomation.atlassian.net";
        LinkedHashMap <String,String> headers = new LinkedHashMap<>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Basic bWlzaHJhLmFpc2h3YXJ5YTk2OUBnbWFpbC5jb206QVRBVFQzeEZmR0YwZlp2YzluS3V1V1plNXgtMTZvTUtYRC1mXy1TaTVEOHozN0RmS0dDRkUxUW10RlhZWmgxYnNySldTNEVUaTdhMWNkSzYxelluVG1EblBuV1lvZFJSYm9OVFhKdFluOGROdEdJNGVwbDMzQlV1WHNnTVhVWjdNdENvNGVIeHhoMzhETmo1YmRuV2x5LXpIdzNXY19hYTNTNkVqdnJRaGcxZjhJNnU4U1piN3ZBPTkxQjA1MzI5");
        String response = given().log().all().headers(headers).pathParam("bugID",issueID).
                when().get("rest/api/3/issue/{bugID}").
                then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(response);
        Assert.assertEquals(js.getString("fields.attachment.filename").replace("[","").replace("]",""),attachment.getName());
    }
}
