package utils;

import extensions.FileExtensions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import log.LogHelper;
import constants.HTMLConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import report.HtmlReportDirectory;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElementUtils {
    private boolean isDebug;
    private static final String CANNOT_TAKE_SCREENSHOT = "Cannot take screenshot";
    private static final String DOUBLE_SLASH = "//";
    public WebDriverWait wait;

    private WebDriver driver;

    public ElementUtils(WebDriver driver,  boolean isDebug) {
        this.driver = driver;
        this.isDebug = isDebug;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //region Highlight Element section
    public void highlightElement(WebElement element, boolean isDebug) {
        if(isDebug){
            JavascriptExecutor jexecu = (JavascriptExecutor) driver;
            jexecu.executeScript("arguments[0].style.backgroundColor='red'", element);
        }
    }

    public void highlightElements(List<WebElement> elements, boolean isDebug) {
        if(isDebug){
            for (WebElement e : elements) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].style.border='3px groove red'", e);
            }
        }
    }
    //endregion

    //region Find Element section
    public WebElement findElement(String elementStr) throws Exception {
        WebElement element = null;
        String[] arr = elementStr.split("=", 2);
        String by = arr[0];
        String value = arr[1];

        try {
            if (by.equalsIgnoreCase(HTMLConstants.ID)) element = driver.findElement(By.id(value));
            else if (by.equalsIgnoreCase(HTMLConstants.XPATH)) element = driver.findElement(By.xpath(value));
            else if (by.equalsIgnoreCase(HTMLConstants.CLASS)) element = driver.findElement(By.className(value));
            else if (by.equalsIgnoreCase(HTMLConstants.CSS)) element = driver.findElement(By.cssSelector(value));
            else if (by.equalsIgnoreCase(HTMLConstants.LINK_TEXT)) element = driver.findElement(By.linkText(value));
            else if (by.equalsIgnoreCase(HTMLConstants.TAG_NAME)) element = driver.findElement(By.tagName(value));
            else if (by.equalsIgnoreCase(HTMLConstants.NAME)) element = driver.findElement(By.name(value));
            else if (by.equalsIgnoreCase(HTMLConstants.PARTIAL_LINK_TEXT))
                element = driver.findElement(By.partialLinkText(value));
        } catch (StaleElementReferenceException e) {
            return findElement(elementStr);
        } catch (Exception e) {
            LogHelper.error("Can not find element as name: " + elementStr);
            throw new Exception("Can not find element as name: " + elementStr);
        }
        return element;
    }

    public List<WebElement> findElements(String elementInfo) throws Exception {
        List<WebElement> listElement = new ArrayList<WebElement>();
        String[] extract = elementInfo.split("=", 2);
        String by = extract[0];
        String value = extract[1];
        try {
            if (by.equalsIgnoreCase(HTMLConstants.ID)) {
                listElement = driver.findElements(By.id(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.XPATH)) {
                listElement = driver.findElements(By.xpath(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.CLASS)) {
                listElement = driver.findElements(By.className(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.CSS)) {
                listElement = driver.findElements(By.cssSelector(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.LINK_TEXT)) {
                listElement = driver.findElements(By.linkText(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.NAME)) {
                listElement = driver.findElements(By.name(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.PARTIAL_LINK_TEXT)) {
                listElement = driver.findElements(By.partialLinkText(value));
            } else if (by.equalsIgnoreCase(HTMLConstants.TAG_NAME)) {
                listElement = driver.findElements(By.tagName(value));
            }
        } catch (StaleElementReferenceException e) {
            return findElements(elementInfo);
        } catch (Exception e) {
            LogHelper.error("Error when find The element located by : [" + elementInfo + "]");
            throw (e);
        }
        return listElement;
    }
    //endregion

    public By getByLocator(String elementStr) throws Exception {
        String[] arr = elementStr.split("=", 2);
        String by = arr[0];
        String value = arr[1];
        By locator = null;

        try {
            if (by.equalsIgnoreCase(HTMLConstants.ID)) locator = By.id(value);
            else if (by.equalsIgnoreCase(HTMLConstants.XPATH)) locator = By.xpath(value);
            else if (by.equalsIgnoreCase(HTMLConstants.CLASS)) locator = By.className(value);
            else if (by.equalsIgnoreCase(HTMLConstants.CSS)) locator = By.cssSelector(value);
            else if (by.equalsIgnoreCase(HTMLConstants.LINK_TEXT)) locator = By.linkText(value);
            else if (by.equalsIgnoreCase(HTMLConstants.TAG_NAME)) locator = By.tagName(value);
            else if (by.equalsIgnoreCase(HTMLConstants.NAME)) locator = By.name(value);
            else if (by.equalsIgnoreCase(HTMLConstants.PARTIAL_LINK_TEXT)) locator = By.partialLinkText(value);
        } catch (Exception e) {
            LogHelper.error("Can not find locator as name: " + elementStr);
            throw new Exception("Can not find locator as name: " + elementStr);
        }

        return locator;
    }

    //endregion

    //region Wait section

    public void waitUntilElementToBeClickable(String elementStr) throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(getByLocator(elementStr)));
            LogHelper.info("Wait until element [" + elementStr + "] to be clickable");
        } catch (StaleElementReferenceException e) {
            waitUntilElementToBeClickable(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot Wait until element [" + elementStr + "] to be clickable");
            throw new Exception("Cannot Wait until element [" + elementStr + "] to be clickable");
        }
    }

    public boolean waitUntilElementInvisibility(String elementStr) throws Exception {
        try {
            if (wait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(elementStr))) != null) {
                LogHelper.info("Wait until element [" + elementStr + "] to be invisible");
                return true;
            }
        } catch (Exception e) {
            LogHelper.error("Cannot wait until element [" + elementStr + "] to be invisible");
            throw new Exception("Cannot wait until element [" + elementStr + "] to be invisible");
        }
        return false;
    }

    public WebDriverWait fluentWaitSettings(WebDriver driver, long timeout, Duration pollingInterval) {
        WebDriverWait fluentWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        fluentWait.pollingEvery(pollingInterval);
        fluentWait.ignoring(NoSuchElementException.class, TimeoutException.class);
        return fluentWait;
    }

    public WebElement fluentWaitForElement(WebDriver driver, By locator, long timeout, Duration pollingInterval) {
        WebElement element = null;
        try {
            element = fluentWaitSettings(driver, timeout, pollingInterval)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException ex) {
            // Handle exceptions as needed
        }
        return element;
    }

    public void isElementDisplayed(String elementStr) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementToBeClickable(elementStr);
            e.isDisplayed();
            LogHelper.info("Element [ " + elementStr + "] is displayed");
        } catch (Exception e) {
            LogHelper.info("Element [ " + elementStr + "] is not displayed");
            throw new Exception("Element [ " + elementStr + "] is not displayed");
        }
    }

    public boolean waitUntilElementVisibility(String elementStr) throws Exception {
        try {
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(elementStr))) != null) {
                LogHelper.info("Wait until element [" + elementStr + "] to be visible");
                return true;
            }
        } catch (Exception e) {
            LogHelper.error("Cannot wait until element [" + elementStr + "] to be visible");
            throw new Exception("Cannot wait until element [" + elementStr + "] to be visible");
        }
        return false;
    }

    public boolean waitUntilElementPresence(String elementStr) throws Exception {
        try {
            if (wait.until(ExpectedConditions.presenceOfElementLocated(getByLocator(elementStr))) != null) {
                LogHelper.info("Wait until element [" + elementStr + "] to be presence");
                return true;
            }
        } catch (Exception e) {
            LogHelper.error("Cannot wait until element [" + elementStr + "] to be presence");
            throw new Exception("Cannot wait until element [" + elementStr + "] to be presence");
        }
        return false;
    }

    public WebElement recursiveWait(String elementStr) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(getByLocator(elementStr)));
        } catch (Exception e) {
            LogHelper.info("Element not found. Retrying...");
            return recursiveWait(elementStr);
        }
    }

    //endregion
    //region action
    public String goToURL(String URL) throws Exception {
        String message = "";
        try {
            driver.get(URL);
            message = "Navigate to " + URL;
            LogHelper.info(message);
        } catch (Exception e) {
            LogHelper.error("Cannot navigate to: " + URL);
            throw new Exception("Cannot navigate to [" + URL + "] ");
        }
        return message;
    }

    public String getCurrentUrl() throws Exception {
        String url = "";
        try {
            url = driver.getCurrentUrl();
            LogHelper.info("Current URL: " + url);
        } catch (Exception e) {
            LogHelper.error("Cannot get current url");
            throw new Exception("Cannot get current url");
        }
        return url;
    }

    public void click(String elementStr) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementToBeClickable(elementStr);
            highlightElement(e, isDebug);
            e.click();
            LogHelper.info("Can click on element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            click(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot click on element [" + elementStr + "]");
            throw new Exception("Cannot click on element [" + elementStr + "]");
        }
    }

    public void clickByJS(String elementStr) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            //  highlightElement(e);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", e);
            LogHelper.info("Can click on element [" + elementStr + "] by using JS");
        } catch (StaleElementReferenceException e) {
            click(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot click on element [" + elementStr + "] by using JS");
            throw new Exception("Cannot click on element [" + elementStr + "] by using JS");
        }
    }

    public void clickUntilReach(String clickedElementStr, String conditionElementStr, String condition) throws Exception {
        try {
            boolean found = false;
            int tryTimes = 0;
            while (!found && tryTimes < 6) {
                click(clickedElementStr);
                found = getTextFromElement(conditionElementStr).replace("$", "").equals(condition);
                tryTimes++;
            }
        } catch (Exception e) {
            LogHelper.error("Cannot click on element [" + clickedElementStr + "]");
            throw new Exception("Cannot click on element [" + clickedElementStr + "]");
        }
    }

    public void scrollDownToElement(String elementStr) throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e = findElement(elementStr);
            highlightElement(e, isDebug);
            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", e);
            LogHelper.info("Can scroll to element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            scrollDownToElement(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot scroll to element [" + elementStr + "]");
            throw new Exception("Cannot scroll to element [" + elementStr + "]");
        }
    }

    public void sendKeys(String elementStr, CharSequence keysToSend) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementToBeClickable(elementStr);
            highlightElement(e, isDebug);
            e.sendKeys(keysToSend);
            LogHelper.info("User has just entered [" + keysToSend + "] into element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            sendKeys(elementStr, keysToSend);
        } catch (Exception e) {
            LogHelper.error("User cannot enter [" + keysToSend + "] into element [" + elementStr + "]");
            throw new Exception("User cannot enter [" + keysToSend + "] into element [" + elementStr + "]");
        }
    }

    public void clear(String elementStr) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementToBeClickable(elementStr);
//            highlightElement(e, isDebug);
            e.clear();
            LogHelper.info("User has just clear element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            clear(elementStr);
        } catch (Exception e) {
            LogHelper.error("User cannot clear element [" + elementStr + "]");
            throw new Exception("User cannot clear element [" + elementStr + "]");
        }
    }

    public void switchToIframe(String elementStr) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            driver.switchTo().frame(e);
            LogHelper.info("Switch to iframe [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            switchToIframe(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot switch to iframe [" + elementStr + "]");
            throw new Exception("Cannot switch to iframe [" + elementStr + "]");
        }
    }

    public String getTitle() throws Exception {
        try {
            String title = driver.getTitle();
            LogHelper.info("Get Title: [" + title + "]successfully");
            return title;
        } catch (Exception e) {
            LogHelper.error("Cannot get Title of the page");
            throw new Exception("Cannot get Title of the page");
        }
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    public void navigateBackPage() {
        driver.navigate().back();
    }

    public void closeNewTabs() {
        ArrayList<String> switchTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(switchTabs.get(1));
        LogHelper.info(switchTabs.get(1));
        driver.close();
        driver.switchTo().window(switchTabs.get(0));
        LogHelper.info(switchTabs.get(0));
    }

    public String getAttriFromElement(String elementStr, String attr) throws Exception {
        String attrText = "";
        try {
            WebElement e = findElement(elementStr);
            attrText = e.getAttribute(attr);
            LogHelper.info("Get text [" + attrText + "] from attribute [" + attr + "] of element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            getAttriFromElement(elementStr, attr);
        } catch (Exception e) {
            LogHelper.error("Cannot get text [" + attrText + "] from attribute [" + attr + "] of element [" + elementStr + "]");
            throw new Exception("Cannot get text [" + attrText + "] from attribute [" + attr + "] of element [" + elementStr + "]");
        }
        return attrText;
    }

    public List<String> getAttriFromElements(String elementStr, String attr) throws Exception {
        List<String> attrText = new ArrayList<>();
        try {
            List<WebElement> e = findElements(elementStr);
            for (WebElement i : e) {
                attrText.add(i.getAttribute(attr));
            }
        } catch (StaleElementReferenceException e) {
            getAttriFromElements(elementStr, attr);
        } catch (Exception e) {
            LogHelper.error("Cannot get text [" + attrText + "] from attribute [" + attr + "] of element [" + elementStr + "]");
            throw new Exception("Cannot get text [" + attrText + "] from attribute [" + attr + "] of element [" + elementStr + "]");
        }
        return attrText;
    }

    public String getTextFromElement(String elementStr) throws Exception {
        String elementText = "";
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementVisibility(elementStr);
            highlightElement(e, isDebug);
            elementText = e.getText();
            LogHelper.info("Get text [" + elementText + "] from element [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            getTextFromElement(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot get text [" + elementText + "] from element [" + elementStr + "]");
            throw new Exception("Cannot get text [" + elementText + "] from element [" + elementStr + "]");
        }
        return elementText;
    }

    public List<String> getTextFromElements(String elementStr) throws Exception {
        List<String> elementText = new ArrayList<>();
        try {
            List<WebElement> listElement = findElements(elementStr);
            for (WebElement e : listElement) {
                elementText.add(e.getText());
            }
        } catch (StaleElementReferenceException e) {
            getTextFromElements(elementStr);
        } catch (Exception e) {
            LogHelper.error("Cannot get text [" + elementText + "] from element [" + elementStr + "]");
            throw new Exception("Cannot get text [" + elementText + "] from element [" + elementStr + "]");
        }
        return elementText;
    }

    public String takeScreenshotOnWebClient(String testName) throws Exception {
        String screenShotPath = "";
        try {
            String screenShotFilename = "";
            Date currentDate = new Date();
            screenShotFilename = currentDate.toString().replace(" ", "-").replace(":", "-") + "-" + testName;

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // store in the location
            screenShotPath = HtmlReportDirectory.getScreenshotFolder() + DOUBLE_SLASH + screenShotFilename + ".png";
            FileExtensions.copyFile(screenshotFile, new File(screenShotPath));
            LogHelper.info("Take screenshot with name [" + screenShotFilename + "] stored at screenshot folder");
        } catch (Exception e) {
            LogHelper.error(CANNOT_TAKE_SCREENSHOT);
            throw new Exception(CANNOT_TAKE_SCREENSHOT);
        }
        return screenShotPath;
    }

    public String takeScreenshotOnBrowserStack(String testName) throws Exception {
        String screenShotPath = "";
        try {
            String screenShotFilename = "";
            Date currentDate = new Date();
            screenShotFilename = currentDate.toString().replace(" ", "-").replace(":", "-") + "-" + testName;

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // store in the location
            screenShotPath = HtmlReportDirectory.getScreenshotFolder() + DOUBLE_SLASH + screenShotFilename + ".png";
            FileExtensions.copyFile(screenshotFile, new File(screenShotPath));
            LogHelper.info("Take screenshot with name [" + screenShotFilename + "] stored at screenshot folder");
        } catch (Exception e) {
            LogHelper.error(CANNOT_TAKE_SCREENSHOT);
            throw new Exception(CANNOT_TAKE_SCREENSHOT);
        }
        return screenShotPath;
    }

    public void selectInDropDownListByValue(String elementStr, String option) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            waitUntilElementToBeClickable(elementStr);
            Select dropdown = new Select(e);
            dropdown.selectByValue(option);
            LogHelper.info("Can select option [" + option + "] in drop down list [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            selectInDropDownListByValue(elementStr, option);
        } catch (Exception e) {
            LogHelper.error("Cannot select option [" + option + "] in drop down list [" + elementStr + "]");
            throw new Exception("Cannot select option [" + option + "] in drop down list [" + elementStr + "]");
        }
    }

    public void selectInDropDownListByText(String elementStr, String option) throws Exception {
        try {
            WebElement e = findElement(elementStr);
            Select dropdown = new Select(e);
            dropdown.selectByVisibleText(option);
            LogHelper.info("Can select option [" + option + "] in drop down list [" + elementStr + "]");
        } catch (StaleElementReferenceException e) {
            selectInDropDownListByText(elementStr, option);
        } catch (Exception e) {
            LogHelper.error("Cannot select option [" + option + "] in drop down list [" + elementStr + "]");
            throw new Exception("Cannot select option [" + option + "] in drop down list [" + elementStr + "]");
        }
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void getWindowHandles(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));
    }
    //endregion

    //region Handle Alert
    public boolean checkAlert() {
        try {
            //Wait 10 seconds till alert is present
            WebDriverWait waitInAlert = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = waitInAlert.until(ExpectedConditions.alertIsPresent());
            //Accepting alert.
            alert.accept();
            LogHelper.info("Accepted the alert successfully.");
            return true;
        } catch (Exception e) {
            LogHelper.error("No Alert");
            return false;
        }
    }

    public String getTextInAlert() {
        String alertText = "";
        try {
            //Wait 10 seconds till alert is present
            WebDriverWait waitInAlert = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = waitInAlert.until(ExpectedConditions.alertIsPresent());
            LogHelper.info("The alert has text: " + alert.getText());
            //Accepting alert.
            alertText = alert.getText();
        } catch (StaleElementReferenceException e) {
            getTextInAlert();
        } catch (Exception e) {
            LogHelper.error("Cannot get text of the alert");
        }
        return alertText;
    }
    //endregion

    //region result on BS
    public void setPassedStatusForBSTest() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Worked as expected\"}}");
    }

    public void setFailedStatusForBSTest() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Error\"}}");
    }
    //endregion

    //Function for Appium driver
    public void rotateDevice(String screenOrientation){
        AndroidDriver androidDriver = (AndroidDriver) driver;
        if(screenOrientation.equalsIgnoreCase("landscape")){
            androidDriver.rotate(ScreenOrientation.LANDSCAPE);
        }
        else {
            androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
    }
}
