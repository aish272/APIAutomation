import inputFiles.ComplexJson;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class L5_testNGAnnotationUsageAndJsonParsing_30 {

    @Test
    public void calculateTotalSales() {

        JsonPath complexJs = new JsonPath(ComplexJson.getComplexJson());
        //6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int i = 0; i < complexJs.getInt("courses.size()"); i++) {
            int price = complexJs.getInt("courses[" + i + "].price");
            int copiesSold = complexJs.getInt("courses[" + i + "].copies");
            sum = sum + (price * copiesSold);
        }
        System.out.println("Total sales calculated " + sum);
        Assert.assertEquals(sum, complexJs.getInt("dashboard.purchaseAmount"));
    }

}
