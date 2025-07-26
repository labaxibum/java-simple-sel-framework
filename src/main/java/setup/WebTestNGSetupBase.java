package setup;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.ConfigLoader;
import utils.ElementUtils;
import driverManager.DriverManager;
import extensions.FileExtensions;
import log.LogHelper;
import extensions.Utils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.HtmlReporter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import static driverManager.DriverCreator.IS_DEBUG;

public class WebTestNGSetupBase {
    private final String testBrowser = ConfigLoader.getConfig("browser");
    public ElementUtils currentBrowser;

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext ctx) {
        HtmlReporter.currentTest = ctx.getName();
        HtmlReporter.createTest(ctx.getName(), "");
        LogHelper.info("============================== Running on test [ " + ctx.getName() + " ] ==============================");
        DriverManager.initWebDriver(testBrowser);
        currentBrowser = new ElementUtils(DriverManager.getWebDriver(), IS_DEBUG);
        DriverManager.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    public WebDriver getDriver() {
        return DriverManager.getWebDriver();
    }

    @BeforeSuite
    public void beforeSuite(ITestContext ctx) throws IOException {
        HtmlReporter.createReport();
        LogHelper.info("============================== Running on browser [ " + testBrowser + " ] ==============================");
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        LogHelper.info("============================== End Test ==============================");
        HtmlReporter.flush();
        Utils.openHTMLFile(FileExtensions.getRootProject() + "/Reports/Latest Report/Report.html".replace("/", Utils.separate()));
    }

    @AfterTest
    public void afterTest() {
        DriverManager.removeWebDriver();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void beforeMethod(@Optional("chrome") String browser, Method method) throws IllegalArgumentException {
        String methodName = method.getName();
        LogHelper.info("########## Running on method [ " + methodName + " ] ##########");
        HtmlReporter.createNode(this.getClass().getSimpleName(), methodName, "");
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            HtmlReporter.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
            String screenShotPath = currentBrowser.takeScreenshotOnWebClient(result.getName());
            String message = "Error: " + result.getThrowable().getMessage();
            LogHelper.error(message);
            HtmlReporter.fail(message, screenShotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            HtmlReporter.getTest().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));

        } else if (result.getStatus() == ITestResult.CREATED || result.getStatus() == ITestResult.SKIP) {
            HtmlReporter.getTest().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
            HtmlReporter.skip(result.getThrowable().toString());
        }
    }

    @BeforeClass
    public void beforeClass() {
        //nothing change with this beforeClass
    }

    @AfterClass
    public void afterClass() {
        //nothing change with this afterClass
    }


}
