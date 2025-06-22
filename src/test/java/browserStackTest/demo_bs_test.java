package browserStackTest;


import org.testng.annotations.Test;
import setup.BrowserStackTestNGSetupBase;


public class demo_bs_test extends BrowserStackTestNGSetupBase {

    @Test(groups = {"group1"}, priority = 0)
    public void getInfo(){
        getDriver().navigate().to("https://google.com");

    }

}
