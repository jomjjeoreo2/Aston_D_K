package tests;

import config.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OnlineReplenishmentPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MTSReplenishTests extends BaseTest {

    private OnlineReplenishmentPage page;

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        super.setup(); // Создает драйвер и wait
        page = new OnlineReplenishmentPage(driver, this);
        page.waitUntilBlockIsReady();
    }

    @Test(priority = 1)
    public void checkBlockTitle() {
        Assert.assertEquals(page.getBlockTitle(), "Онлайнпополнениебезкомиссии");
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

        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();
    }


    @Test(dataProvider = "servicesData", priority = 5)
    public void checkPlaceholdersAndLabelsForAllServices(String serviceName, Map<String, String> expected) {
        page.chooseService(serviceName);

        Assert.assertEquals(page.getPhonePlaceholder(), expected.get("phone"));
        Assert.assertEquals(page.getAmountLabel(), expected.get("sum"));
        Assert.assertEquals(page.getEmailPlaceholder(), expected.get("email"));
    }


    @Test(priority = 6)
    public void checkMobilePaymentDetailsInModal() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));


        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();


        page.waitForCardPopup(wait);


        Assert.assertTrue(page.getPayButtonText().contains("10"), "Сумма 10 не отображена на кнопке Оплатить");


        Assert.assertEquals(page.getCardNumberPlaceholder(), "XXXX XXXX XXXX XXXX");
        Assert.assertEquals(page.getExpiryPlaceholder(), "MM / YY");
        Assert.assertEquals(page.getCvcPlaceholder(), "CVC/CID");


        Assert.assertTrue(page.arePaymentIconsVisible(), "Отсутствуют иконки Visa/Mastercard в окне оплаты");
    }



    @DataProvider(name = "servicesData")
    public Object[][] provideServices() {
        return new Object[][]{
                {"Услуги связи", createLabels("+375", "Руб.", "E-mail для отправки чека")},
                {"Домашний интернет", createLabels("+375", "Руб.", "E-mail для отправки чека")},
                {"Рассрочка", createLabels("Номер счета на 44", "Руб.", "E-mail для отправки чека")},
                {"Задолженность", createLabels("Номер счета на 2073", "Руб.", "E-mail для отправки чека")}
        };
    }

    private Map<String, String> createLabels(String phone, String sum, String email) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("sum", sum);
        map.put("email", email);
        return map;
    }
}