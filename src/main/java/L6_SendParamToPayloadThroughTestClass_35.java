import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import inputFiles.*;

public class L6_SendParamToPayloadThroughTestClass_35 {

    @Test
    public void addBookAndSendBookDetailsViaTest() {
        RestAssured.baseURI ="http://216.10.245.166";
        String givenResponse = given().log().all().header("Content-Type","application/json").
                body(LibraryAPIPayloadJson.getAddBookPayload("9870","bottom")).
                when().post("Library/Addbook.php").then().log().all().extract().response().asString();
        JsonPath addBook = new JsonPath(givenResponse);
        String bookId = addBook.getString("ID");
        System.out.println(bookId);
    }
}
