package helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.SuiteConfiguration;

import java.net.URL;

public class DriverHelper {

    public static final String browserName = "chrome";
    public static final String browserVersion = "102.0";
    private WebDriver driver;
    public SuiteConfiguration configuration;
    public static boolean headless;
    public static String grid;

    private void setUpScreenResolution() {
        if (headless) {
            driver.manage().window().setSize(new Dimension(1920, 1200));
        } else {
            driver.manage().window().maximize();
        }
    }

    @SneakyThrows
    public WebDriver getInitDriver() {
        if (grid.equals("${grid}")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(grid),
                    configuration.getSelenoidCapabilities(browserName, browserVersion));
            remoteWebDriver.setFileDetector(new LocalFileDetector());
            driver = remoteWebDriver;
        }
        setUpScreenResolution();

        return driver;
    }
}
