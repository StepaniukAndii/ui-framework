package utils;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    public static WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    private static final int BASE_TIME = 15;

    @SneakyThrows
    public static void forPageLoaded(WebDriver driver) {
        try {
            for (int i = 0; i < 50; i++) {
                if (driver.findElement(By.xpath("//div[@class='nuxt-global-loader']")).isDisplayed()) {
                    Thread.sleep(200);
                } else {
                    break;
                }
            }
        } catch (Exception ignore) {
        }

        ExpectedCondition<Boolean> condition = dvr -> {
            assert dvr != null;
            return ((JavascriptExecutor) dvr)
                    .executeScript("return document.readyState")
                    .toString().equals("complete");
        };
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(BASE_TIME));
            wait.until(condition);
        } catch (Throwable e) {
            Assert.fail("Page with did not load with in 15 sec");
        }
    }

    public static boolean waitUrlContains(WebDriver driver, String url) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(BASE_TIME));
            wait.until(ExpectedConditions.urlContains(url));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean numberOfWindowsToBe(int number) {
        try {
            return wait.until(ExpectedConditions.numberOfWindowsToBe(number));
        } catch (Exception e) {
            return false;
        }
    }
}
