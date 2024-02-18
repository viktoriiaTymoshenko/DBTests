package DBAuto.Tests;

import DBAuto.Pages.CommonPage;
import DBAuto.Pages.LandingPage;
import DBAuto.Pages.SearchPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


@Epic("Ticket search tests")
public class SearchTest {
    LandingPage landingPage = new LandingPage();
    SearchPage searchPage = new SearchPage();
    CommonPage commonPage = new CommonPage();


    @Description("Smoke test for search")
    @ParameterizedTest
    @CsvFileSource(files = "src/test/java/DBAuto/resources/cities.csv", numLinesToSkip = 1)
    public void checkMainSearch(String startPoint, String endPoint) {
        try {
            landingPage.openLandingPage()
                    .allowCookies()
                    .enterSearchPoints(startPoint, endPoint);
            searchPage.search()
                    .validateTableResult(startPoint, endPoint);
        } catch (Throwable error) {
            Allure.step(error.getMessage());
            Allure.attachment("error screen", commonPage.attachScreenshot());
            throw error;
        }
    }

    @Description("Check round trip with parameters")
    @Test
    public void searchRoundTripWithParameters() {
        String startPointValue = "Bonn";
        String endPointValue = "Hamburg";
        int daysToAddToStart = 2;
        int daysToAddToReturn = 3;
        String formatWithYear = "dd/MM/yyyy";
        String formatWithoutYear = "dd. MMM";
        String name = "Viktoriia";
        String lastName = "Tymoshenko";
        String email = "Kunde.karla@gmx.de";
        try {
            landingPage.openLandingPage()
                    .allowCookies()
                    .enterSearchPoints(startPointValue, endPointValue)
                    .selectStartDate(daysToAddToStart, formatWithYear)
                    .selectReturnDate(daysToAddToReturn, formatWithYear)
                    .addPassenger()
                    .checkSelectedParameters(daysToAddToStart, daysToAddToReturn, formatWithoutYear, 3);
            searchPage.search()
                    .validateTableResult(startPointValue, endPointValue)
                    .ticketSelection(daysToAddToReturn, formatWithoutYear)
                    .classOfferSelection()
                    .withoutLoginSelection()
                    .enterCustomerData(name, lastName, email);
        } catch (Throwable error) {
            Allure.step(error.getMessage());
            Allure.attachment("error screen", commonPage.attachScreenshot());
            throw error;
        }

    }


}
