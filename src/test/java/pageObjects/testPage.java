package pageObjects;

import controls.Button;
import driverManager.DriverCreator;
import driverManager.DriverManager;
import org.openqa.selenium.WebDriver;
import utils.ElementUtils;

public class testPage extends ElementUtils {
    private String locLoginBtn = "xpath=//header//a[@aria-label='Gmail ']";
    private String locImageBtn = "xpath=//header//a[@data-pid='2']";
    public testPage(WebDriver driver) {
        super(driver, DriverCreator.IS_DEBUG);
    }

    public void clickOntoLogin() throws Exception {
        click(locLoginBtn);
    }
    public void clickOntoImage() throws Exception {
        click(locImageBtn);
    }
}
