package extensions;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import config.ConfigLoader;
import log.LogHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Utils {
    public static final String OS_VERSION = "os_version";
    public static final String DEVICE = "device";
    public static final String REAL_MOBILE = "real_mobile";
    public static final String BS_LOCAL = "browserstack.local";
    public static final String BS_DEBUG = "browserstack.debug";
    public static final String URL = "https://" + ConfigLoader.getConfig("username_bs") + ":" + ConfigLoader.getConfig("password_bs") + "@hub-cloud.browserstack.com/wd/hub";


    //Get the game name and change it to normal name
    public static String changeGameNameToNormalGameName(String gameName) {
        if (gameName != null) {
            return StringUtils.capitalize(gameName);
        } else {
            return "please input game name";
        }
    }

    //Sleep function
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LogHelper.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    //get Random string base on formatted String
    public static String getRandomString() {
        String RANDOM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();

    }

    //get Random string base on formatted String
    public static String getRandomSpecialString() {
        String RANDOM = "BinhLe1234";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();

    }

    //get Random String in the random 1->9
    public static String getRandomNumber() {
        String RANDOM = "1234567890";
        StringBuilder random = new StringBuilder();
        Random rnd = new Random();
        while (random.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOM.length());
            random.append(RANDOM.charAt(index));
        }
        return random.toString();
    }

    /**
     * Generates a random alphabetic string of the specified length.
     *
     * @param index The length of the random string to generate
     * @return A randomly generated alphabetic string of the given length
     */
    public static String getLongString(int index) {
        return RandomStringUtils.randomAlphabetic(index);
    }

    /**
     * Splits a string on a given character and returns the substring at the specified indeString jsonFilePath = FileExtensions.getRootProject() + FileReaderManager.getConfigReader().getMobileDataTestJsonFilePath("MenuCriticalCheckingNewAccount");x.
     *
     * @param inputtedString The original string to split
     * @param character      The character to split the string on
     * @param index          The index of the substring to return
     * @return The substring from the split inputtedString at the given index
     */
    public static String splitStringOnUsingCharacterAndIndex(String inputtedString, String character, int index) {
        String[] splitString = inputtedString.split(character);
        return splitString[index];
    }

    //get Random number for 1 to 80
    public static Integer getRandomNumberFrom1To80() {
        Random random = new Random();
        int low = 1;
        int high = 80;
        return random.nextInt(high - low) + low;
    }

    //get datetime base on the formatted
    public static String getDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Get String data from JSON file by attribute

    /**
     * Gets a specific attribute value from a JSON file.
     *
     * @param jsonFilePath Path of the JSON file
     * @param attribute    Name of the attribute to extract
     * @return The attribute value as a string, empty string if not found
     */
    public static String getDataFromJsonFile(String jsonFilePath, String attribute) {
        JSONParser parser = new JSONParser();
        String data = "";
        try {
            // Read the JSON file
            Object obj = parser.parse(new FileReader(jsonFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            // Extract the required data
            data = (String) jsonObject.get(attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Switches the webdriver control to a newly opened tab/window.
     *
     * @param driver The webdriver instance
     */
    public static void switchingToNewTab(WebDriver driver) {
        ArrayList<String> newTab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
    }

    /**
     * Randomly selects an element from a list.
     *
     * @param listElement           The list to select an element from
     * @param numberOfPickedElement The number of elements to randomly select
     * @return A randomly selected element from the list
     */
    public static String getElementInGame(List<String> listElement, int numberOfPickedElement) {
        String randomElement = "";
        Random rand = new Random();
        List<String> givenList = listElement;
        for (int i = 0; i < numberOfPickedElement; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            randomElement = givenList.get(randomIndex);
        }
        return randomElement;
    }

    /**
     * Removes all non-alphanumeric characters from the given string.
     *
     * @param inputtedString The original string
     * @return The string with all special characters removed
     */
    public static String replaceAllSpecialCharacter(String inputtedString) {
        return inputtedString.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Opens the most recently downloaded file on the user's machine.
     *
     * @throws Exception If file open fails
     */
    public static void openTheLatestDownloadedFile() throws Exception {
        File downloadsFolder = new File(System.getProperty("user.home") + "/Downloads");
        File[] files = downloadsFolder.listFiles();
        File latestFile = Arrays.stream(files != null ? files : new File[0]).filter(File::isFile).max(Comparator.comparing(File::lastModified)).orElse(null);
        LogHelper.info("\nFile:" + (latestFile != null ? latestFile.getName() : null));
        Desktop.getDesktop().open(latestFile);
    }

    /**
     * Extracts the user token from a given URL.
     *
     * @param url The URL to extract the token from
     * @return The decoded user token string
     * @throws Exception If URL is invalid or token cannot be extracted
     */
    public static String getUserToken(String url) throws Exception {
        String[] parts = url.split("UserToken=");
        String[] temps = parts[1].split("&SessionId");
        return URLDecoder.decode(temps[0], "UTF-8");
    }

    public static String getFakeUSCreditCardNumber(CreditCardType creditCardType) {
        Faker fakerWithLocales = new Faker(Locale.US);
        return fakerWithLocales.finance().creditCard(creditCardType).replace("-", "");
    }

    public static String decodeURL(String url) throws Exception {
        return URLDecoder.decode(url, "UTF-8");
    }
    public static void writeToJSONFile(String objectName, String value, String folderType, String fileName) {
        String fileDirectory;
        if (folderType.equals("web")) {
            fileDirectory = String.format("/src/test/resources/jsonFiles/jsonTestDataFile/webTest/%s.json", fileName);
        } else {
            fileDirectory = String.format("/src/test/resources/jsonFiles/jsonTestDataFile/mobileTest/%s.json", fileName);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(objectName, value);
        try {
            FileWriter file = new FileWriter(FileExtensions.getRootProject() + fileDirectory);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("Error when processing the JSON file");
        }
    }

    public static void clearCacheChrome(WebDriver driver){
        driver.get("chrome://settings/clearBrowserData");
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.TAB);
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
    }

    public static String separate() {
        return System.getProperty("file.separator");
    }

    public static void openHTMLFile(String url) throws IOException {
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    public static String getLast4Digit(String creditCardNumber) {
        if (creditCardNumber.length() >= 4) {
            return "ending in " + creditCardNumber.substring(creditCardNumber.length() - 4);
        } else {
            return "Please input correct creditCardNumber";
        }
    }

//    public static List<Point> customMatchTemplate(String imgSrcPath, String imgTemplatePath, double threshold) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        // Read source and template images
//        Mat imgSrc = Imgcodecs.imread(imgSrcPath);
//        Mat imgTemplate = Imgcodecs.imread(imgTemplatePath);
//
//        // Remove alpha channel if it exists
//        Imgproc.cvtColor(imgSrc, imgSrc, Imgproc.COLOR_BGRA2BGR);
//        Imgproc.cvtColor(imgTemplate, imgTemplate, Imgproc.COLOR_BGRA2BGR);
//
//        // Perform template matching
//        Mat result = new Mat();
//        Imgproc.matchTemplate(imgSrc, imgTemplate, result, Imgproc.TM_CCOEFF_NORMED);
//
//        // Find matches above threshold
//        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
//        double maxVal = mmr.maxVal;
//
//        List<Point> matches = new ArrayList<>();
//        if (maxVal >= threshold) {
//            threshold = Math.max(threshold, maxVal - 0.01);
//
//            Mat mask = new Mat();
//            Core.compare(result, new Scalar(threshold), mask, Core.CMP_GE);
//
//            Mat locations = new Mat();
//            Core.findNonZero(mask, locations);
//
//            for (int i = 0; i < locations.rows(); i++) {
//                double[] loc = locations.get(i, 0);
//                matches.add(new Point(loc[0], loc[1]));
//            }
//        }
//        return matches;
//    }
}
