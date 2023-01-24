package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.ExamplePage;

public class TestExample extends TestBase {

    @Test
    @Description("")
    public void checkOpenRozetka() {
        ExamplePage examplePage = new ExamplePage(driver);

        examplePage
                .open()
                .search("Холодильник");

//        assertEquals(getUrl(), env + "hi");
    }
}
