package DBAuto.Tests;

import DBAuto.Pages.CommonPage;
import DBAuto.Pages.LandingPage;
import DBAuto.Pages.SearchPage;
import io.qameta.allure.*;
import org.junit.Test;


@Epic("Ticket search tests")
public class SearchTest {
    LandingPage landingPage = new LandingPage();
    SearchPage searchPage = new SearchPage();
    CommonPage commonPage = new CommonPage();


    @Description ("Smoke test for search")
    @Test
    public void checkMainSearch() {
        String startPointValue = "Berlin";
        String endPointValue = "Düsseldorf";
        try {
            landingPage.openLandingPage();
            landingPage.allowCookies();
            landingPage.enterSearchPoints(startPointValue, endPointValue);
            searchPage.search();
            searchPage.validateTableResult(startPointValue, endPointValue);
        } catch (Throwable error ){
            Allure.step(error.getMessage());
            Allure.attachment("error screen",commonPage.attachScreenshot() );
            throw error;
        }
    }

    @Description("Check round trip with parameters")
    @Test
    public void searchRoundTripWithParameters(){
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
            landingPage.openLandingPage();
            landingPage.allowCookies();
            landingPage.enterSearchPoints(startPointValue, endPointValue);
            landingPage.selectStartDate(daysToAddToStart, formatWithYear);
            landingPage.selectReturnDate(daysToAddToReturn,formatWithYear);
            landingPage.addPassenger();
            landingPage.checkSelectedParameters(daysToAddToStart,daysToAddToReturn, formatWithoutYear,3);
            searchPage.search();
            searchPage.validateTableResult(startPointValue,endPointValue);
            searchPage.ticketSelection(daysToAddToReturn,formatWithoutYear);
            searchPage.classOfferSelection();
            searchPage.withoutLoginSelection();
            searchPage.enterCustomerData(name,lastName,email);
        } catch (Throwable error ){
            Allure.step(error.getMessage());
            Allure.attachment("error screen",commonPage.attachScreenshot() );
            throw error;
        }

    }


}
