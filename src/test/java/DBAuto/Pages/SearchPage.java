package DBAuto.Pages;

import DBAuto.Pages.CommonPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByShadow;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchPage extends CommonPage {
    LandingPage landingPage = new LandingPage();

    private By searchButton = By.cssSelector(".quick-finder-basic__search-btn");
    private By reiseElementItem = By.cssSelector(".verbindung-list__result-item--0");
    private By applyTicketButton = By.cssSelector(".verbindung-list__result-item--0 .reiseloesung-button-container__btn-waehlen");
    private By ticketListDateHeader = By.cssSelector(".buchungsstrecke-heading__title");
    private By offersStep = By.xpath("//*[@data-page='Angebote']");
    private By classOfferSelectionButton = By.cssSelector(".angebot-details-buttons__auswaehlen");
    private By seatReservCheckbox = By.cssSelector(".platzreservierung-label");
    //By.cssSelector("#reservierung [type='checkbox']");
    private By confirmOffersBtn = By.id("btn-weiter");
    private String customerUrl = "/kundendaten";
    private By withoutLoginBox = By.xpath("//*[@for = 'anmeldungauswahl-anonym']");
    private By title = By.cssSelector(".test-name-anrede");
    private By firsNameField = By.xpath("//*[@autocomplete = 'given-name']");
    private By lastNameField = By.xpath("//*[@autocomplete = 'family-name']");
    private By emailField = By.name("kundenkonto-kontakt-email");
    private By frauTitleOption = By.xpath("//*[@data-value='FR']");
    String url = "fahrplan/zahlung";

    private By ticketPoints(String ort) {
        return By.xpath("//span[@title = '" + ort + " Hbf']");
    }

    @Step("Start search")
    public SearchPage search() {
        $(searchButton).shouldBe(Condition.enabled).click();
        waiter = new WebDriverWait(webdriver().object(), Duration.ofSeconds(10));
        return this;
    }

    @Step("Validate table with result of search")
    public SearchPage validateTableResult(String startPointValue, String endPointValue) {
        $$(reiseElementItem).first().shouldBe(visible).shouldHave(Condition.partialText(startPointValue), Condition.partialText(endPointValue));
        return this;
    }

    @Step("Ticket selection")
    public SearchPage ticketSelection(int date, String format) {
        $(applyTicketButton).shouldBe(visible).click();
        $(ticketListDateHeader).should(have(text(landingPage.generateDate(date, format))));
        $(applyTicketButton).shouldBe(visible).click();
        $(offersStep).shouldBe(visible);
        return this;
    }

    @Step("Select class of the trip")
    public SearchPage classOfferSelection() {
        $(classOfferSelectionButton).shouldBe(Condition.enabled).click();
        $(seatReservCheckbox).shouldBe(Condition.enabled).click();
        waiter.until(ExpectedConditions.elementToBeClickable($(confirmOffersBtn)));
        $(confirmOffersBtn).shouldBe(enabled).click();
        return this;
    }

    @Step("Select seat")
    public SearchPage withoutLoginSelection() {
        waiter.until(ExpectedConditions.elementToBeClickable($(withoutLoginBox)));
        $(withoutLoginBox).shouldBe(visible).shouldBe(enabled).click();
        $(confirmOffersBtn).shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Enter customer data")
    public SearchPage enterCustomerData(String firstName, String lastName, String email) {
        $(title).shouldBe(enabled).click();
        waiter = new WebDriverWait(webdriver().object(), Duration.ofSeconds(5));
        $(frauTitleOption).shouldBe(enabled).click();
        $(firsNameField).shouldBe(enabled).sendKeys(firstName);
        $(lastNameField).shouldBe(enabled).sendKeys(lastName);
        $(emailField).shouldBe(enabled).sendKeys(email);
        $(confirmOffersBtn).shouldBe(Condition.enabled).click();
        waiter.until(ExpectedConditions.urlContains(url));
        validateUrl(url);
        return this;
    }


}
