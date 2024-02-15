package DBAuto.Pages;

import DBAuto.Pages.CommonPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.selector.ByShadow;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.PrivateKey;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertTrue;

public class LandingPage extends CommonPage {

    String mainUrl = "https://www.bahn.de/";
    private By startPointField = By.name("quickFinderBasic-von");
    private By endPointField = By.name("quickFinderBasic-nach");
    private By PointDDvalue(String ort) {
        return By.xpath("//*[contains(@data-value, '" + ort + " Hbf')]");
    }

    private By calendarStart = By.cssSelector(".quick-finder-options__hinfahrt");
    private By calendarReturn = By.cssSelector(".quick-finder-options__rueckfahrt-container");
    private By addPassenger = By.xpath("//*[@data-test-id='qf-reisende']");
    private By numberSelectedPassenger = By.xpath("//*[@data-test-id='qf-reisende']//*[@class = 'quick-finder-option-area__sublabel']");
    private By addPassengerContainer = By.className("button-overlay-body-container__body");
    private By passengerNumberField = By.id("reisendeAnzahl-0");
    private By passengerNumber = By.xpath("//*[@id='reisendeAnzahl-0-list']//*[@data-value = '2']");
    private By addTravelersButton = By.className("ReisendeHinzufuegenButton");
    private By addNewPassengerType = By.id("reisendeTyp-1");
    private By dog = By.xpath("//*[@id='reisendeTyp-1-list']//*[@data-value='HUND']");
    private By dataEnterField = By.cssSelector(".db-web-date-picker-input__field");
    private By acceptButton = By.xpath("//*[@data-test-id ='quick-finder-save-button']");
    private By clearStartIcon = By.cssSelector(".test-von-halt .icon-clear");
    private By clearReturnIcon = By.cssSelector(".test-nach-halt .icon-clear");
    String hostShadowElement = "body > div:nth-child(1)";
    String targetShadowElement = ".js-accept-all-cookies";

    @Step("Open DB main page")
    public void openLandingPage() {
        open(mainUrl);
        waiter = new WebDriverWait(webdriver().object(), Duration.ofSeconds(5));
        currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        validateUrl(mainUrl);
    }
    @Step("Accept cookies")
    public void allowCookies() {
        try{
            waiter.until(ExpectedConditions.elementToBeClickable(ByShadow.cssSelector(targetShadowElement,hostShadowElement)));
            SelenideElement element = $(ByShadow.cssSelector(targetShadowElement, hostShadowElement));
            element.click();
        }catch (TimeoutException e){
            System.out.println("No cookie dialog");
        }}

    @Step("Enter start point {startPointValue} and end point {endPointValue}")
    public void enterSearchPoints(String startPointValue, String endPointValue) {
        if (!($(startPointField).is(empty))){
            $(clearStartIcon).shouldBe(visible).click();
        }
        $(startPointField).shouldBe(Condition.enabled).setValue(startPointValue);
        $(PointDDvalue(startPointValue)).shouldBe(Condition.enabled).click();
        if (!($(endPointField).is(empty))){
            $(clearReturnIcon).shouldBe(visible).click();
        }
        $(endPointField).shouldBe(Condition.enabled).setValue(endPointValue);
        $(PointDDvalue(endPointValue)).shouldBe(Condition.enabled).click();

    }
    @Step("Select start date")
    public void selectStartDate(int day, String format){
        $(calendarStart).shouldBe(Condition.visible).click();
        $(dataEnterField).shouldBe(Condition.enabled).click();
        $(dataEnterField).sendKeys(generateDate(day,format));
        $(acceptButton).shouldBe(Condition.enabled).click();
    }
    @Step("Select return date")
    public void selectReturnDate(int day, String format){
        $(calendarReturn).shouldBe(Condition.visible).click();
        $(dataEnterField).shouldBe(Condition.enabled).click();
        $(dataEnterField).sendKeys(generateDate(day,format));
        $(acceptButton).shouldBe(Condition.enabled).click();

    }

    public String generateDate(int daysToAdd, String format) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.GERMAN);
        return futureDate.format(formatter);
    }
    @Step("Select passengers ")
    public void addPassenger(){
        $(addPassenger).shouldBe(Condition.enabled).click();
        $(addPassengerContainer).shouldBe(Condition.visible);
        $(passengerNumberField).shouldBe(Condition.enabled).click();
        $(passengerNumber).shouldBe(Condition.enabled).click();
        $(addTravelersButton).shouldBe(Condition.enabled).click();
        $(addNewPassengerType).shouldBe(Condition.enabled).click();
        $(dog).shouldBe(Condition.enabled).click();
        $(acceptButton).click();
    }
    @Step("Check selected parameters")
    public void checkSelectedParameters(int dateStart, int dateReturn, String format, int passengers ){
        String startDate =generateDate(dateStart, format);
        String returnDate =generateDate(dateReturn, format);
        $(calendarStart).should(have(text(startDate)));
        $(calendarReturn).should(have(text(returnDate)));
        $(addPassenger).should(have(text(String.valueOf(passengers))));
    }

}
