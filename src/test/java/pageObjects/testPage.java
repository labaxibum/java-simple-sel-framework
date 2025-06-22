package pageObjects;

import org.openqa.selenium.WebDriver;
import utils.ElementUtils;

public class testPage extends ElementUtils {
    private String locLoginBtn = "xpath=//a[text()='Login']";
    public testPage(WebDriver driver) {
        super(driver);
    }

    public void clickOntoLogin() throws Exception {
        click(locLoginBtn);
    }
}
