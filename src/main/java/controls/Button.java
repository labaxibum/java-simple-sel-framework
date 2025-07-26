package controls;

import driverManager.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementUtils;

import static driverManager.DriverCreator.IS_DEBUG;

public class Button extends ElementUtils {

    private final String locator;

    public Button(String element) {
        super(DriverManager.getWebDriver(), IS_DEBUG);
        locator = element;
    }

    public void clickButton() throws Exception {
        super.click(locator);
    }
}
