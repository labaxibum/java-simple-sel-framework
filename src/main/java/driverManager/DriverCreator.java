package driverManager;

import config.ConfigLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DriverCreator {
    private static final String PLATFORM_NAME_BS = "platformName";
    private static final String PLATFORM_VERSION_BS = "platformVersion";
    private static final String DEVICE_NAME_BS = "deviceName";
    private static final String BUILD_NAME_BS = "buildName";
    private static final String PROJECT_NAME_BS = "projectName";
    public static final String URL_BS = "https://" + ConfigLoader.getConfig("username_bs") + ":" + ConfigLoader.getConfig("password_bs") + "@hub-apse.browserstack.com/wd/hub";

    public static WebDriver getWebDriver(String browser) {
        WebDriver webDriver = null;
        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                options.setCapability("goog:loggingPrefs", logPrefs);
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.addArguments("--mute-audio");
                options.setExperimentalOption("prefs", prefs);
                webDriver = new ChromeDriver(options);
                webDriver.manage().window().maximize();
            }
            case "firefox" -> {
                webDriver = new FirefoxDriver();
            }
            case "edge" -> {
                webDriver = new EdgeDriver();
            }
        }
        return webDriver;
    }

    public static RemoteWebDriver getBrowserStackDriver(String device, String osVersion, String deviceOrientation) throws MalformedURLException {
        RemoteWebDriver browserStackDriver = null;

        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put(BUILD_NAME_BS, ConfigLoader.getConfig("buildName_bs"));
        browserstackOptions.put(PROJECT_NAME_BS, ConfigLoader.getConfig("projectName_bs"));
        browserstackOptions.put("deviceOrientation", deviceOrientation);
        browserstackOptions.put(PLATFORM_VERSION_BS, osVersion);
        browserstackOptions.put(DEVICE_NAME_BS, device);
        browserstackOptions.put("consoleLogs", "info");
        capabilities.setCapability("bstack:options", browserstackOptions);
        capabilities.setCapability(PLATFORM_NAME_BS, "android");

        browserStackDriver = new RemoteWebDriver(new URL(URL_BS), capabilities);
        return browserStackDriver;
    }
}
