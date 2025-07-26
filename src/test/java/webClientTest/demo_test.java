package webClientTest;

import api.APIUtils;
import api.APIHelper;
import driverManager.DriverManager;
import extensions.Utils;
import log.LogHelper;
import org.apache.http.HttpResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.testPage;
import report.HtmlReporter;

import webClientTest.setup.WebTestNGSetup;


public class demo_test extends WebTestNGSetup {
    testPage test;

    @BeforeTest(alwaysRun = true)
    public void setUpBeforeTest() throws Exception {
        test = new testPage(getDriver());
        currentBrowser.goToURL("https://google.com");
    }

    @Test(groups = "1")
    public void openPage2() throws Exception {
        System.out.println("The thread ID for test1 is " + Thread.currentThread().getId());
        //currentBrowser.waitUntilElementToBeClickable("xpath=//a[text()='Login']");
        test.clickOntoLogin();
        Utils.sleep(3000);
    }

    @Test(groups = "2")
    public void openPage1() throws Exception {
        currentBrowser.goToURL("https://facebook.com");
        System.out.println("The thread ID for test2 is " + Thread.currentThread().getId());
        //currentBrowser.waitUntilElementToBeClickable("xpath=//a[text()='Login']");
        test.clickOntoImage();
        Utils.sleep(3000);
    }


//    @Test(priority = 0, enabled = false)
//    public void goToPage() throws Exception {
//        LogHelper.info("Before login" + Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b")));
//        String cookie = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
//        String accessToken = APIHelper.parseCookieToJson(cookie, "access_token", "value");
//        // String userURL = apiHelper.parseCookieToJson(cookie, "", "user_url");
//        Map<String, String> header = new HashMap<>();
//        header.put("X-Kore-returnurl", "");
//        header.put("Authorization", String.format("Bearer %s", accessToken));
//        String json = "";
//        HttpResponse response = APIUtils.sendPOSTRequestDynamicHeaders("", json, header);
//
//        LogHelper.info(response.getStatusLine().getStatusCode());
//        DriverManager.getWebDriver().navigate().refresh();
//        Utils.sleep(3000);
//        String cookie1 = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
//        String accessToken1 = APIHelper.parseCookieToJson(cookie1, "access_token", "value");
//        String userURL = APIHelper.parseCookieToJson(cookie1, "", "user_url");
//        Map<String, String> header1 = new HashMap<>();
//        header.put("X-Kore-ReturnUrl", "");
//        header.put("Authorization", String.format("Bearer %s", accessToken1));
//        LogHelper.info(APIUtils.getGETJSONObjectWithDynamicHeaders(userURL + "/stats", header1));
//        Utils.sleep(3000);
//    }
//
//    @Test(priority = 1, enabled = false)
//    public void getCredential() throws Exception {
//        DriverManager.getWebDriver().navigate().refresh();
//        String cookie = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
//        String accessToken = APIHelper.parseCookieToJson(cookie, "access_token", "value");
//        String userURL = APIHelper.parseCookieToJson(cookie, "", "user_url");
//        Map<String, String> header = new HashMap<>();
//        header.put("X-Kore-ReturnUrl", "");
//        header.put("Authorization", String.format("Bearer %s", accessToken));
//        LogHelper.info(APIUtils.getGETJSONObjectWithDynamicHeaders(userURL + "/stats", header));
//    }
//
//    @Test(priority = 1, enabled = false)
//    public void apiCheck() throws IOException {
//
//        HtmlReporter.info(APIUtils.getGETJSONResponse("", "application/json").toString());
//    }
}
