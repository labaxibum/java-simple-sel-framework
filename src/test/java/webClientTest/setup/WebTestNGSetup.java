package webClientTest.setup;

import driverManager.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import setup.WebTestNGSetupBase;

import java.io.IOException;
import java.lang.reflect.Method;

public class WebTestNGSetup extends WebTestNGSetupBase {
    public WebDriver getDriver() {
        return DriverManager.getWebDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext ctx) throws IOException {
        super.beforeSuite(ctx);
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException {
        super.afterSuite();
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext ctx) {
        super.beforeTest(ctx);
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        super.afterTest();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        super.beforeClass();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(@Optional("chrome") String browser, Method method) throws IllegalArgumentException {
        super.beforeMethod(browser, method);
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws Exception {
        super.getResult(result);
    }

    @AfterClass
    public void afterClass() {
        super.afterClass();
    }
}