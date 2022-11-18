package tests;

import helper.DriverHelper;
import listener.TestListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.SuiteConfiguration;
import utils.WaitUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

@Listeners(TestListener.class)
public abstract class TestBase extends Assert {

    public String env = "https://localhost:8080";
    protected WebDriver driver;
    private final Logger logger = LogManager.getLogger(TestBase.class);

    @BeforeClass
    public void initTestSuite() {
        SuiteConfiguration config = new SuiteConfiguration();
        DriverHelper.headless = Boolean.parseBoolean(config.getProperty("headless"));
        DriverHelper.grid = config.getProperty("grid");
    }

    @BeforeMethod
    public void initDriver(Method method) {
        driver = new DriverHelper().getInitDriver();
        logger.info("----- WebDriver initialized -----" + method.getName());
    }

    @AfterMethod
    public void quiteDriver(Method method) {
        driver.quit();
        logger.info("----- Close WebDriver -----" + method.getName());
    }

    @AfterSuite
    public void afterSuite() {
        addAllureProperties();
    }

    private void addAllureProperties() {
        File f = new File("target/allure-results/environment.properties");

        Properties props = new Properties();
        props.setProperty("URL", env);
        props.setProperty("Browser", DriverHelper.browserName);
        props.setProperty("Browser Version", DriverHelper.browserVersion);

        try {
            if (f.createNewFile()) {
                OutputStream out = new FileOutputStream(f);
                props.store(out, "Allure report environment variables");
            } else {
                logger.warn("Allure environment.properties file exists");
            }
        } catch (Exception e) {
            logger.error("Allure environment.properties file was not created");
        }
    }

    public String getUrl() {
        WaitUtils.forPageLoaded(driver);
        return driver.getCurrentUrl();
    }

    public String getUrlWithoutParameters() {
        String currentUrl = driver.getCurrentUrl();

        int index = currentUrl.indexOf("?");
        if (index >= 0)
            currentUrl = currentUrl.substring(0, index);

        return currentUrl;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public void close() {
        driver.close();
    }

    protected void sleep(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
