package inputFiles;

import org.testng.annotations.DataProvider;

public class AddBook2DArray {

    @DataProvider(name = "BookData")
    public Object[][] returnBookData()
    {
        return new Object[][]{{"9809","quirky"},{"6978","worky"},{"6594","farq7"}};
    }
}
