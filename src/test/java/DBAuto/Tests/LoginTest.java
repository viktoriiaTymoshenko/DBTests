package DBAuto.Tests;

import DBAuto.Pages.CommonPage;
import DBAuto.Pages.LandingPage;
import DBAuto.Pages.LogInPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Test;


@Epic("Login test")
public class LoginTest {
    LandingPage landingPage = new LandingPage();
    LogInPage logInPage = new LogInPage();
    CommonPage commonPage = new CommonPage();
    @Description("Smoke test for Login")
    @Test
    public void loginTest(){
        String username = "Kunde.karla@gmx.de";
        try{
        landingPage.openLandingPage();
        landingPage.allowCookies();
        logInPage.login();
        logInPage.enterUserName(username);
        logInPage.returnToMainPage();
    }  catch (Throwable error ){
        Allure.step(error.getMessage());
        Allure.attachment("error screen",commonPage.attachScreenshot() );
        throw error;
    }
}}
