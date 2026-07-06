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
    /* Первая часть (твои рабочие тесты) */

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

    // ✅ Четвертый тест (интеграция в новую структуру)
    @Test(priority = 4)
    public void checkConnectionForm() {
        // Закрытие куки уже встроено в BaseTest
        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();
    }

    /* Вторая часть задания */

    // Requirement #1: Проверка надписей в незаполненных полях
    @Test(dataProvider = "servicesData", priority = 5)
    public void checkPlaceholdersAndLabelsForAllServices(String serviceName, Map<String, String> expected) {
        page.chooseService(serviceName);

        // Проверяем активные поля
        Assert.assertEquals(page.getPhonePlaceholder(), expected.get("phone"));
        Assert.assertEquals(page.getAmountLabel(), expected.get("sum"));
        Assert.assertEquals(page.getEmailPlaceholder(), expected.get("email"));
    }

    // Requirement #2: Проверка модального окна (услуги связи)
    @Test(priority = 6)
    public void checkMobilePaymentDetailsInModal() {
        // Настройка ожидания
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Заполняем форму
        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();

        // 2. Ждем появления модального окна
        page.waitForCardPopup(wait);

        // 3. Проверяем сумму на кнопке
        Assert.assertTrue(page.getPayButtonText().contains("10"), "Сумма 10 не отображена на кнопке Оплатить");

        // 4. Проверяем плацебохолдеры полей карты
        Assert.assertEquals(page.getCardNumberPlaceholder(), "XXXX XXXX XXXX XXXX");
        Assert.assertEquals(page.getExpiryPlaceholder(), "MM / YY");
        Assert.assertEquals(page.getCvcPlaceholder(), "CVC/CID");

        // 5. Проверяем иконки платёжных систем
        Assert.assertTrue(page.arePaymentIconsVisible(), "Отсутствуют иконки Visa/Mastercard в окне оплаты");
    }

    /* === DATA PROVIDER === */

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