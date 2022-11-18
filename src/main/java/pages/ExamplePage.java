package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExamplePage extends BasePage {

    public ExamplePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ExamplePage open() {
        openUrl(env);
        return this;
    }

    @FindBy(xpath = "//input")
    private WebElement searchField;

    public ExamplePage search(String text) {
        sendText(searchField, text);
        return this;
    }
}
