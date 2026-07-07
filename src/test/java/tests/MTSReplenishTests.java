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
import java.util.List;
import java.util.Map;

import static pages.OnlineReplenishmentPage.FIELDS_PER_SERVICE;

public class MTSReplenishTests extends BaseTest {

    private OnlineReplenishmentPage page;

    private WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        super.setup();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        page = new OnlineReplenishmentPage(driver, this);
        page.waitUntilBlockIsReady();
    }

    @Test(priority = 1)
    public void checkBlockTitle() {
        String title = page.getBlockTitle().replaceAll("\\s+", "");
        Assert.assertEquals(title, "ОНЛАЙНПОПОЛНЕНИЕБЕЗКОМИССИИ");
    }

    @Test(priority = 2)
    public void checkPaymentLogos() {
        Assert.assertTrue(page.hasPartnersLogos(), "Нет логотипов платёжных систем");
    }

    @Test(priority = 3)
    public void checkMoreInfoLink() {
        Assert.assertTrue(page.isInfoLinkPresent(), "Ссылка «Подробнее о сервисе» не найдена");
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

        List<String> relevantFields = FIELDS_PER_SERVICE.get(serviceName);
        for (String field : relevantFields) {
            String actualValue;
            switch (field) {
                case "phone":
                    actualValue = page.getPhonePlaceholder();
                    break;
                case "account":
                    String expectedAccount = expected.get("account");
                    if (expectedAccount == null || expectedAccount.trim().isEmpty()) {
                        continue;
                    }
                    actualValue = page.getAccountPlaceholder();
                    break;
                case "sum":
                    actualValue = page.getAmountLabel();
                    break;
                case "email":
                    actualValue = page.getEmailPlaceholder();
                    break;
                default:
                    throw new IllegalStateException("Неизвестное поле: " + field);
            }

            String expectedValue = expected.get(field);
            Assert.assertEquals(actualValue, expectedValue,
                    "Для услуги '" + serviceName + "' поле '" + field + "' не совпадает.\n" +
                            "Ожидалось: '" + expectedValue + "'\n" +
                            "Получено: '" + actualValue + "'");
        }
    }

    @Test(priority = 6)
    public void checkMobilePaymentDetailsInModal() {
        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();

        page.waitForCardPopup(wait);

        String payButtonText = page.getPayButtonText();
        boolean sumOrPayPresent = payButtonText.contains("10") || payButtonText.contains("Оплатить");
        Assert.assertTrue(sumOrPayPresent,
                "На кнопке оплаты не отображается сумма или текст «Оплатить». Получено: '" + payButtonText + "'");

        Assert.assertEquals(page.getCardNumberPlaceholder(), "XXXX XXXX XXXX XXXX",
                "Неверный placeholder для номера карты");
        Assert.assertEquals(page.getExpiryPlaceholder(), "MM / YY",
                "Неверный placeholder для срока действия");
        Assert.assertEquals(page.getCvcPlaceholder(), "CVC/CID",
                "Неверный placeholder для CVC");

        Assert.assertTrue(page.arePaymentIconsVisible(), "Отсутствуют иконки платёжных систем в окне оплаты");
    }

    @DataProvider(name = "servicesData")
    public Object[][] provideServices() {
        return new Object[][]{
                {"Услуги связи", createLabels("Номер телефона", "", "Руб.", "E-mail для отправки чека")},
                {"Домашний интернет", createLabels("Номер телефона", "", "Руб.", "E-mail для отправки чека")},
                {"Рассрочка", createLabels("", "Номер счёта на 44", "Руб.", "E-mail для отправки чека")},
                {"Задолженность", createLabels("", "Номер счёта на 2073", "Руб.", "E-mail для отправки чека")}
        };
    }

    private Map<String, String> createLabels(String phone, String account, String sum, String email) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("account", account);
        map.put("sum", sum);
        map.put("email", email);
        return map;
    }
}
