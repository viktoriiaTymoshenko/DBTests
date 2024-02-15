package DBAuto.Pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;

public class CommonPage {

    String currentUrl;
    WebDriverWait waiter ;
    @Step("Validation of URL")
    public void validateUrl(String url) {
        currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertTrue(currentUrl.contains(url));
    }


    public ByteArrayInputStream attachScreenshot() {
        return new ByteArrayInputStream(Selenide.screenshot(OutputType.BYTES));
    }

}
