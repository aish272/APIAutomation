import inputFiles.AddBook2DArray;
import inputFiles.LibraryAPIPayloadJson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class L7_UseDataProviderToSendDynamicParam_36_37 {

    @Test(dataProvider = "BookData",dataProviderClass = AddBook2DArray.class)
    public void addBookAndFetchDataThroughDataProvider( String ISBN,String aisle) {
        RestAssured.baseURI ="http://216.10.245.166";
        String givenResponse = given().header("Content-Type","application/json").
                body(LibraryAPIPayloadJson.getAddBookPayload(ISBN,aisle)).
                when().post("Library/Addbook.php").then().extract().response().asString();
        JsonPath addBook = new JsonPath(givenResponse);
        System.out.println(addBook.getString("ID"));

    }

    @Test(dataProvider = "BookData",dataProviderClass = AddBook2DArray.class)
    public void deleteBookAndFetchDataThroughDataProvider(String ISBN,String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

       given().body(LibraryAPIPayloadJson.getDeleteBookPayload(ISBN+aisle)).log().all()
               .when().post("/Library/DeleteBook.php").then().log().all().extract().response();


    }

}
