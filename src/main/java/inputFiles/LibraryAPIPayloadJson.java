package inputFiles;

public class LibraryAPIPayloadJson {
    public static String getAddBookPayload( String ISBN,String aisle)
    {
        return "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+ISBN+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";
    }
    public static String getDeleteBookPayload(String bookID)
    {
        return "{\n" +
                " \n" +
                "\"ID\" : \""+bookID+"\"\n" +
                " \n" +
                "} \n";
    }
}
