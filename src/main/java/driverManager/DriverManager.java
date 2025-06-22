package driverManager;


import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.Set;

public class DriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<RemoteWebDriver> browserStackDriver = new ThreadLocal<>();

    private DriverManager() {
    }

    //region WebDriver "Chrome" section
    public static void initWebDriver(String browser) {
        WebDriver _webDriver = DriverCreator.getWebDriver(browser);
        webDriver.set(_webDriver);
    }

    public static void removeWebDriver() {
        getWebDriver().quit();
    }

    public static WebDriver getWebDriver() {
        return webDriver.get();
    }

    public static Set<Cookie> getCookie() {
        return getWebDriver().manage().getCookies();
    }

    public static Cookie getCookieByName(String name) {
        return getWebDriver().manage().getCookieNamed(name);
    }
    //endregion

    //region Appium BrowserStack "DeviceName" section
    public static void initBrowserStackDriver(String device, String osVersion, String deviceOrientation) throws MalformedURLException {
        RemoteWebDriver _browserStackDriver = DriverCreator.getBrowserStackDriver(device, osVersion, deviceOrientation);
        browserStackDriver.set(_browserStackDriver);
    }

    public static RemoteWebDriver getBrowserStackDriver() {
        return browserStackDriver.get();
    }

    public static void quitBrowserStackDriver() {
        getBrowserStackDriver().quit();
    }
    //endregion
}
