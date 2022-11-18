package utils;

import helper.DriverOptions;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Loads test suite configuration from resource files.
 */
public class SuiteConfiguration {

    private static final String APPLICATION_PROPERTIES = "/application.properties";

    private final Properties properties;

    @SneakyThrows
    public SuiteConfiguration() {
        properties = new Properties();
        properties.load(SuiteConfiguration.class.getResourceAsStream(System.getProperty("application.properties", APPLICATION_PROPERTIES)));
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public DesiredCapabilities getSelenoidCapabilities(String browserName, String browserVersion) {
        DesiredCapabilities capabilities = new DesiredCapabilities(DriverOptions.getOptions());
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", false,
                "enableVideo", false
        ));
        return capabilities;
    }
}
