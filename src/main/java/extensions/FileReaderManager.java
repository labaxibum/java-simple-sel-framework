package extensions;

public class FileReaderManager {
    public static final FileReaderManager fileReaderManager = new FileReaderManager();
    private static JsonReaderUtils jsonDataReader;

    public static JsonReaderUtils getJsonReader(String testName){
        return (jsonDataReader == null) ? new JsonReaderUtils(testName) : jsonDataReader;
    }
}
