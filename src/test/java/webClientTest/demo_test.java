package webClientTest;

import api.APIUtils;
import api.apiHelper;
import config.ConfigLoader;
import driverManager.DriverManager;
import extensions.Utils;
import log.LogHelper;
import org.apache.http.HttpResponse;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import report.HtmlReporter;
import setup.WebTestNGSetupBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class demo_test extends WebTestNGSetupBase {


    @BeforeTest
    public void openPage() throws Exception {
        currentBrowser.goToURL("https://google.com");
        currentBrowser.waitUntilElementToBeClickable("xpath=//a[text()='Login']");
        Utils.sleep(3000);
    }


    @Test(priority = 0)
    public void goToPage() throws Exception {
        LogHelper.info("Before login" + Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b")));
        String cookie = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
        String accessToken = apiHelper.parseCookieToJson(cookie, "access_token", "value");
        // String userURL = apiHelper.parseCookieToJson(cookie, "", "user_url");
        Map<String, String> header = new HashMap<>();
        header.put("X-Kore-returnurl", "");
        header.put("Authorization", String.format("Bearer %s", accessToken));
        String json = "";
        HttpResponse response = APIUtils.sendPOSTRequestDynamicHeaders("", json, header);

        LogHelper.info(response.getStatusLine().getStatusCode());
        DriverManager.getWebDriver().navigate().refresh();
        Utils.sleep(3000);
        String cookie1 = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
        String accessToken1 = apiHelper.parseCookieToJson(cookie1, "access_token", "value");
        String userURL = apiHelper.parseCookieToJson(cookie1, "", "user_url");
        Map<String, String> header1 = new HashMap<>();
        header.put("X-Kore-ReturnUrl", "");
        header.put("Authorization", String.format("Bearer %s", accessToken1));
        LogHelper.info(APIUtils.getGETJSONObjectWithDynamicHeaders(userURL + "/stats", header1));
        Utils.sleep(3000);
    }

    @Test(priority = 1, enabled = false)
    public void getCredential() throws Exception {
        DriverManager.getWebDriver().navigate().refresh();
        String cookie = Utils.decodeURL(DriverManager.getCookieByName("").getValue().replace("+", "%2b"));
        String accessToken = apiHelper.parseCookieToJson(cookie, "access_token", "value");
        String userURL = apiHelper.parseCookieToJson(cookie, "", "user_url");
        Map<String, String> header = new HashMap<>();
        header.put("X-Kore-ReturnUrl", "");
        header.put("Authorization", String.format("Bearer %s", accessToken));
        LogHelper.info(APIUtils.getGETJSONObjectWithDynamicHeaders(userURL + "/stats", header));
    }

    @Test(priority = 1, enabled = false)
    public void apiCheck() throws IOException {

        HtmlReporter.info(APIUtils.getGETJSONResponse("", "application/json").toString());
    }
}
