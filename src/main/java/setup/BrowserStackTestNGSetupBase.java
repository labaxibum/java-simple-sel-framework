package setup;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import dataobjects.DevicesOnBS;
import driverManager.DriverManager;
import extensions.FileExtensions;
import extensions.FileReaderManager;
import extensions.JsonReaderUtils;
import extensions.Utils;
import log.LogHelper;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.HtmlReporter;
import utils.ElementUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BrowserStackTestNGSetupBase {

    private String device;
    public ElementUtils currentBrowser;

    DevicesOnBS jSonData;

    public RemoteWebDriver getDriver() {
        return DriverManager.getBrowserStackDriver();
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext ctx) {
        HtmlReporter.currentTest = ctx.getName();
        HtmlReporter.createTest(ctx.getName(), "");
        LogHelper.info("============================== Running on test [ " + ctx.getName() + " ] ==============================");

    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext ctx) throws IOException {
        HtmlReporter.createReport();
        LogHelper.info("============================== Running on BrowserStack ==============================");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException {
        LogHelper.info("============================== End Test ==============================");
        HtmlReporter.flush();
        Utils.openHTMLFile(FileExtensions.getRootProject() + "/Reports/Latest Report/Report.html".replace("/", Utils.separate()));
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        DriverManager.quitBrowserStackDriver();
    }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(@Optional("chrome") String browser, Method method) throws IllegalArgumentException {
        String methodName = method.getName();
        LogHelper.info("########## Running on method [ " + methodName + " ] ##########");
        HtmlReporter.createNode(this.getClass().getSimpleName(), methodName, "");
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            HtmlReporter.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
            String screenShotPath = currentBrowser.takeScreenshotOnBrowserStack(result.getName());
            String message = "Error: " + result.getThrowable().getMessage();
            currentBrowser.setFailedStatusForBSTest();
            HtmlReporter.fail(message, screenShotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            HtmlReporter.getTest().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
            currentBrowser.setPassedStatusForBSTest();
        } else if (result.getStatus() == ITestResult.CREATED || result.getStatus() == ITestResult.SKIP) {
            HtmlReporter.getTest().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
            HtmlReporter.skip(result.getThrowable().toString());
        }
    }

    @Parameters({"device"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String deviceXML) throws MalformedURLException {
        device = deviceXML;
        jSonData = FileReaderManager.getJsonReader("DeviceOnBS").getDeviceByDeviceTest(device);
        DriverManager.initBrowserStackDriver(jSonData.deviceName, jSonData.osVersion, jSonData.deviceOrientation);
        currentBrowser = new ElementUtils(DriverManager.getBrowserStackDriver());
        DriverManager.getBrowserStackDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterClass
    public void afterClass() {
        //nothing change with this afterClass
    }
}
