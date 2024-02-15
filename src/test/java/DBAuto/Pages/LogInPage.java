package DBAuto.Pages;

import DBAuto.Pages.CommonPage;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LogInPage extends CommonPage {

    private By loginButton = By.id("js-login-nav");
    private By usernameField = By.id("username");
    private By accessButton = By.name("login");
    private By backButton = By.id("mobile-back-button-link");
    private String url = "https://accounts.bahn.de/auth/";
    private By title = By.cssSelector("#kc-page-title .login-view");

    @Step("Navigation to the login page")
    public void login(){
        $(loginButton).shouldBe(Condition.enabled).click();
        validateUrl(url);
        $(title).shouldBe(Condition.visible);
    }
    @Step("Enter user name and check that password button is presented")
    public void enterUserName(String user){
        $(usernameField).shouldBe(Condition.visible).setValue(user);
        $(accessButton).shouldBe(Condition.visible).click();
    }

    @Story("Return to the main page")
    public void returnToMainPage(){
        $(backButton).shouldBe(Condition.visible).click();
    }

}
