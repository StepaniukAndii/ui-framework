package helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.SuiteConfiguration;

import java.net.URL;
import java.util.Map;

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
            driver = new ChromeDriver((ChromeOptions) DriverOptions.getOptions());
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities(DriverOptions.getOptions());
            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("browserVersion", browserVersion);
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", false,
                    "enableVideo", false
            ));
            driver = new RemoteWebDriver(new URL(grid), capabilities);
        }
        setUpScreenResolution();

        return driver;
    }
}
