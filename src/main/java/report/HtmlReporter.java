package report;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import log.LogHelper;
import org.testng.Reporter;
import report.markup.MarkupHelperPlus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HtmlReporter {
    private static final Boolean IS_RELATIVE_SCREENSHOT = true;
    public static String currentTest;
    private static ExtentReports extentReport;
    private static HashMap<String, ExtentTest> extentTestMap = new HashMap<>();
    private static ExtentTest testClass;
    private static ExtentTest testCase;

    public static ExtentReports createReport() throws IOException {

        if (extentReport == null) {
            extentReport = createInstance();
        }
        extentReport.setAnalysisStrategy(AnalysisStrategy.CLASS);
        return extentReport;
    }

    private static ExtentReports createInstance() throws IOException {
        HtmlReportDirectory.initReportDirectory();

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(HtmlReportDirectory.getReportFilePath());
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Automation Report");
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        ExtentReports report = new ExtentReports();
        report.attachReporter(htmlReporter);
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Application", "Automation Test");
        LogHelper.info("report path:" + HtmlReportDirectory.getReportFilePath());

        return report;
    }

    public static void flush() {
        if (extentReport != null) {
            extentReport.flush();
            extentReport = null;
        }
    }

    public static String getValueOfXmlFile(String value) {
        return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(value);
    }

    public static ExtentTest createTest(String strTestMethodName, String strTestMethodDesc) {

        testClass = extentReport.createTest(strTestMethodName, strTestMethodDesc);
        return testClass;
    }

    public static ExtentTest createTest(String strTestClassName) {

        testClass = extentReport.createTest(strTestClassName);
        return testClass;
    }

    public static ExtentTest createNode(String strClassName, String strTestMethodName,
                                        String strTestMethodDesc) {
        testCase = testClass.createNode(strTestMethodName);
        return testCase;
    }

    public static ExtentTest getTest() {
        return testCase;
    }

    private static String getScreenshotPath(String strAbsolutePath) {
        if (IS_RELATIVE_SCREENSHOT) {
            return new File(HtmlReportDirectory.getReportFolder()).toPath().relativize(new File(strAbsolutePath).toPath()).toString();
        } else {
            return "file:///" + strAbsolutePath;
        }
    }

    public static void pass(String strDescription) {
        getTest().pass(strDescription);
    }

    public static void pass(Markup m) {
        getTest().pass(m);
    }

    public static void warning(String strDescription) {
        getTest().warning(strDescription);
    }

    public static void info(String strDescription) {
        getTest().info(strDescription);
    }

    /**
     * Takes a screenshot and embeds it into the test report.
     *
     * @param strScreenshotPath Path to save the screenshot to
     * @throws IOException If screenshot capture fails
     */
    public static void screenShot(String strScreenshotPath) throws IOException {
        strScreenshotPath = getScreenshotPath(strScreenshotPath);
        getTest().addScreenCaptureFromPath(strScreenshotPath);
    }

    public static void pass(String strDescription, String strScreenshotPath) throws IOException {
        if (strScreenshotPath.equalsIgnoreCase("")) {
            getTest().pass(strDescription);
        } else {
            strScreenshotPath = getScreenshotPath(strScreenshotPath);
            getTest().pass(strDescription).addScreenCaptureFromPath(strScreenshotPath);
        }
    }

//    public static void fail(String strDescription, Throwable throwable, MediaEntityModelProvider build) {
//        getTest().fail(strDescription);
//    }
//
//    public static void fail(Markup m, MediaEntityModelProvider build) {
//        getTest().fail(m);
//    }

    public static void fail(String strDescription, Throwable e) {
        getTest().fail(strDescription).fail(e);
    }

    public static void fail(String strDescription, String strScreenshotPath) throws IOException {
        if (strScreenshotPath.equalsIgnoreCase("")) {
            getTest().fail(strDescription);
        } else {
            strScreenshotPath = getScreenshotPath(strScreenshotPath);
            getTest().fail(strDescription).addScreenCaptureFromPath(strScreenshotPath);
        }
    }

    public static void fail(String strDescription, Throwable e, String strScreenshotPath) throws IOException {
        if (strScreenshotPath.equalsIgnoreCase("")) {
            getTest().fail(strDescription).fail(e);
        } else {
            strScreenshotPath = getScreenshotPath(strScreenshotPath);
            getTest().fail(strDescription).fail(e).addScreenCaptureFromPath(strScreenshotPath);
        }
    }

    public static void skip(String strDescription) throws IOException {
        getTest().skip(strDescription);
    }

    public static void skip(String strDescription, Throwable e) throws IOException {
        getTest().skip(strDescription).fail(e);
    }

    public static void skip(String strDescription, String strScreenshotPath) throws IOException {
        if (strDescription.equalsIgnoreCase("")) {
            getTest().skip(strDescription);
        } else {
            strScreenshotPath = getScreenshotPath(strScreenshotPath);
            getTest().skip(strDescription).addScreenCaptureFromPath(strScreenshotPath);
        }
    }

    public static void skip(String strDescription, Throwable e, String strScreenshotPath) throws IOException {
        if (strDescription.equalsIgnoreCase("")) {
            getTest().skip(strDescription).skip(e);
        } else {
            strScreenshotPath = getScreenshotPath(strScreenshotPath);
            getTest().skip(strDescription).skip(e).addScreenCaptureFromPath(strScreenshotPath);
        }
    }

    public static void label(String strDescription) {
        getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.BLUE));
    }

    public static void labelFailed(String strDescription) {
        getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.RED));
    }

    public static void labelSkipped(String strDescription) {
        getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.AMBER));
    }

    public static void labelWarning(String strDescription) {
        getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));
    }

    public static void description(String data) throws IllegalArgumentException {
        getTest().info(MarkupHelper.createCodeBlock(data));
    }

    public static void testMarkup(List<String> expectedList, List<String> actualList){
        getTest().info(MarkupHelperPlus.test(expectedList,actualList));
    }
}
