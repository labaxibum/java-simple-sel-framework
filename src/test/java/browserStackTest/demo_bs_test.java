package browserStackTest;


import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;
import pageObjects.testPage;
import setup.BrowserStackTestNGSetupBase;


public class demo_bs_test extends BrowserStackTestNGSetupBase {

    @Test(groups = {"group1"}, priority = 0)
    public void getInfo() throws Exception {
        getDriver().navigate().to("https://google.com");
        currentBrowser.rotateDevice("portrait");
        testPage testPage = new testPage(getDriver());
        String btnLogin  = "xpath=//a[@aria-label='Sign in']";
        testPage.click(btnLogin);
    }

}
