import inputFiles.ComplexJson;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class L4_parseComplexJson_27_28_29 {

    public static void main(String[] args) {

        JsonPath complexJs = new JsonPath(ComplexJson.getComplexJson());

        //1. Print No of courses returned by API
        int numOfCourses = complexJs.getInt("courses.size()");
        System.out.println("Num of courses:"+numOfCourses);
        System.out.println("____________________________________________________");

        //2.Print Purchase Amount
        System.out.println("Purchase amt of courses:"+complexJs.getInt("dashboard.purchaseAmount"));
        System.out.println("____________________________________________________");

        //3. Print Title of the first course
        System.out.println("Title of the first course:"+complexJs.getString("courses[0].title"));
        System.out.println("____________________________________________________");

        //4. Print All course titles and their respective Prices
        for(int i=0;i<numOfCourses;i++)
        {
            System.out.println("Title of course "+(i+1)+": "+complexJs.getString("courses["+i+"].title").toString());
            System.out.println("Price of course "+(i+1)+": "+complexJs.getInt("courses["+i+"].price"));
        }
        System.out.println("____________________________________________________");

        //5. Print no of copies sold by RPA Course
        for(int i=0;i<numOfCourses;i++)
        {
            if(complexJs.getString("courses["+i+"].title").toString().equals("RPA"))
            {
                System.out.println("Copies of RPA course "+complexJs.getString("courses["+i+"].copies"));
                break;
            }
        }
        System.out.println("____________________________________________________");

        //6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum =0;
        for(int i=0;i<numOfCourses;i++)
        {
            int price = complexJs.getInt("courses["+i+"].price");
            int copiesSold = complexJs.getInt("courses["+i+"].copies");
            sum = sum+(price*copiesSold);
        }
        System.out.println("Total sales calculated "+sum);
        Assert.assertEquals(sum,complexJs.getInt("dashboard.purchaseAmount"));


    }


}
