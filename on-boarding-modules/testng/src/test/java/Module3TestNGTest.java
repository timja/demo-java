import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Module3TestNGTest {
    private WebDriver driver;

    /** Here we moved all prerequisite tasks into a setup method such as:
     *  creating the WebDriver session
     *  defining capabilities etc.*/
    @BeforeMethod
    public void setupTestMEthod() throws MalformedURLException {
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceURL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";

        /**
         * In this exercise use the Platform Configurator, located here:
         * https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
         * in order to replace the following DesiredCapabilities: browserName, platform, and version
         * For example, I chose to use Windows 10 with Chrome version 59.
         * Note: If you use Chrome version 61+ you must use the sauce:options capability.
         * More info here: https://wiki.saucelabs.com/display/DOCS/Selenium+W3C+Capabilities+Support+-+Beta
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("version", "59.0");
        capabilities.setCapability("build", System.getenv("SAUCE_BUILD_NAME"));
        capabilities.setCapability("name", "3-cross-browser");

        /** If you're accessing the EU data center, use the following endpoint:.
         * https://ondemand.eu-central-1.saucelabs.com/wd/hub
         * */
        driver = new RemoteWebDriver(new URL(sauceURL), capabilities);

        String message = String.format("SauceOnDemandSessionID=%1$s", (((RemoteWebDriver) driver).getSessionId()).toString());
        System.out.println(message);
    }

    @Test
    public void shouldOpenChrome() {
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertTrue(true);

    }
    /**
     * Below we are performing 2 critical actions. Quitting the driver and passing
     * the test result to Sauce Labs user interface.
     */
    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
}
