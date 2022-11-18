package listener;

import io.qameta.allure.Attachment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.TestBase;

public class TestListener implements ITestListener {

    Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("---------------------------------------------------------------");
        logger.info("Failed because of - " + result.getThrowable());
        logger.info("---------------------------------------------------------------");
        WebDriver driver = ((TestBase) result.getInstance()).getDriver();
        if (driver != null) {
            captureScreen(driver);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("---------------------------------------------------------------");
        logger.info("Skipped because of - " + result.getThrowable());
        logger.info("---------------------------------------------------------------");
        WebDriver driver = ((TestBase) result.getInstance()).getDriver();
        if (driver != null) {
            captureScreen(driver);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("---------------------------------------------------------------");
        logger.info(result.getMethod().getMethodName() + " Started");
        logger.info("---------------------------------------------------------------");
        logger.info(result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("---------------------------------------------------------------");
        logger.info(result.getMethod().getMethodName() + " Passed");
        logger.info("---------------------------------------------------------------");
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("===============================================================");
        logger.info("     On Start :-" + context.getName() + "                      ");
        logger.info("===============================================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("===============================================================");
        logger.info("     On Finish :-" + context.getName() + "                     ");
        logger.info("===============================================================");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] captureScreen(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
