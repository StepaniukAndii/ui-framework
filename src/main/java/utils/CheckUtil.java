package utils;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    public static boolean isPresent(WebElement element) {
        try {
            element.isDisplayed();
            logger.info("Element present");
            return true;
        } catch (Exception e) {
            logger.info("Element is not present");
            return false;
        }
    }

    public static boolean isPresentInList(String item, List<String> list) {
        logger.info("Check " + item + " item is present in list");
        return list.stream().anyMatch(s -> s.equals(item));
    }
}
