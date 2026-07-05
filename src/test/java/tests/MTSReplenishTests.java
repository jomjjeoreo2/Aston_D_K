package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import config.BaseTest;
import pages.OnlineReplenishmentPage;

public class MTSReplenishTests extends BaseTest {

    private OnlineReplenishmentPage page;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        super.setup();
        page = new OnlineReplenishmentPage(driver);
    }

    @Test(priority = 1)
    public void checkBlockTitle() {
        String title = page.getBlockTitle().replaceAll("\\s+", "");
        Assert.assertEquals(title, "ОНЛАЙНПОПОЛНЕНИЕБЕЗКОМИССИИ");
    }

    @Test(priority = 2)
    public void checkPaymentLogos() {

        Assert.assertTrue(page.hasPartnersLogos(), "Нет логотипов платежных систем");
    }

    @Test(priority = 3)
    public void checkMoreInfoLink() {
        Assert.assertTrue(page.isInfoLinkPresent());
    }

    @Test(priority = 4)
    public void checkConnectionForm() {
        closeCookies();

        page.chooseService("Услуги связи");
        page.enterPhone("+375297777777");
        page.enterAmount("10");
        page.clickSubmit();
    }
}