package webClientTest;

import extensions.Utils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.testPage;
import webClientTest.setup.WebTestNGSetup;


public class demo_parallel_test extends WebTestNGSetup {
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
}
