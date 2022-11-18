package pages;

import io.qameta.allure.Step;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Conditions;
import utils.WaitUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jse;
    protected final Logger logger;
    public final String env = "https://rozetka.com.ua";

    public abstract <T> T open();

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        jse = (JavascriptExecutor) driver;
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Step("Open url {0}")
    protected void openUrl(String url) {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
        WaitUtils.forPageLoaded(driver);
    }

    protected WebElement findElement(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    protected List<WebElement> findElements(String locator) {
        return driver.findElements(By.xpath(locator));
    }

    protected WebElement waitElement(String locator, Conditions conditions) {
        switch (conditions) {
            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
            case VISIBILITY:
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            default:
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
        }
    }

    protected WebElement waitElement(WebElement element, Conditions conditions) {
        if (Conditions.CLICKABLE == conditions) {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected List<WebElement> waitElements(String locator, Conditions conditions) {
        if (conditions == Conditions.VISIBILITY) {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
        }
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
    }

    protected WebElement getElement(String locator) {
        return waitElement(locator, Conditions.PRESENT);
    }

    protected WebElement getVisibilityElement(String locator) {
        return waitElement(locator, Conditions.VISIBILITY);
    }

    protected WebElement getClickableElement(String locator) {
        return waitElement(locator, Conditions.CLICKABLE);
    }

    protected List<WebElement> getElements(String locator) {
        return waitElements(locator, Conditions.PRESENT);
    }

    protected List<WebElement> getVisibilityOfElements(String locator) {
        return waitElements(locator, Conditions.VISIBILITY);
    }

    protected boolean waitInvisibility(String locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
    }

    protected boolean waitInvisibility(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Step
    protected String getText(WebElement toGetText) {
        return toGetText.getText();
    }

    @Step
    protected void sendText(WebElement toSendText, String text) {
        toSendText.sendKeys(text);
    }

    @Step
    protected void clear(WebElement toClear) {
        waitElement(toClear, Conditions.CLICKABLE).clear();
    }

    @Step
    public void refreshCurrentPage() {
        driver.navigate().refresh();
        WaitUtils.forPageLoaded(driver);
    }

    @Step
    public void switchToBrowserTab(int tabIndex) {
        ArrayList<String> availableWindows = new ArrayList<>(driver.getWindowHandles());
        if (!availableWindows.isEmpty()) {
            driver.switchTo().window(availableWindows.get(tabIndex));
        }
    }

    @Step
    protected void switchToNewTab() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    @Step
    protected void scrollToElement(WebElement element) {
        String script = "const rect = arguments[0].getBoundingClientRect(); return ( rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));";

        jse.executeScript("arguments[0].scrollIntoView(true);", element);

        try {
            for (int i = 0; i < 10; i++) {
                String view = jse.executeScript(script, element).toString();
                if (view.equals("false")) {
                    Thread.sleep(100);
                } else {
                    break;
                }
            }
        } catch (Exception ignore) {
        }
    }

    @Step
    protected void clickWithJS(WebElement field) {
        jse.executeScript("arguments[0].click();", field);
    }

    @Step
    protected void setFieldValueWithJS(WebElement field, String value) {
        jse.executeScript("arguments[0].value='" + value + "';", field);
    }

    protected String getXPath(WebElement element) {
        String fullText = element.toString();
        return fullText.substring(fullText.indexOf("xpath: ") + 7, fullText.length() - 1);
    }

    @Step
    public String getValue(WebElement element) {
        return element.getAttribute("value");
    }
}
