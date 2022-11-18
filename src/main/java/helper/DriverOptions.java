package helper;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import static helper.DriverHelper.headless;

public class DriverOptions {

    public static MutableCapabilities getOptions() {
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }
        return options;
    }
}
