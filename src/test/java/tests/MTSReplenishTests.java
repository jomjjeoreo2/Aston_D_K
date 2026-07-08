package tests;

import config.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Step;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OnlineReplenishmentPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pages.OnlineReplenishmentPage.FIELDS_PER_SERVICE;

@Epic("Онлайн-пополнение")
@Feature("Страница онлайн-пополнения")
@Story("Проверка элементов страницы и форм")
public class MTSReplenishTests extends BaseTest {

    private OnlineReplenishmentPage page;

    @BeforeMethod
    public void initPage() {
        System.out.println("=== Инициализируем страницу ===");
        if (driver == null) {
            System.out.println("ОШИБКА: driver == null!");
            throw new IllegalStateException("driver не инициализирован в BaseTest.setup()");
        } else {
            this.page = new OnlineReplenishmentPage(driver);
            System.out.println("Страница успешно создана: " + this.page);
        }
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void checkBlockTitle() {
        String title = page.getBlockTitle().replaceAll("\\s+", "");
        Assert.assertEquals(title, "ОНЛАЙНПОПОЛНЕНИЕБЕЗКОМИССИИ",
                "Заголовок блока не совпадает с ожидаемым. Получено: '" + title + "'");
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.NORMAL)
    public void checkPaymentLogos() {
        Assert.assertTrue(page.hasPartnersLogos(), "Нет логотипов платёжных систем");
    }

    @Test(priority = 3)
    @Severity(SeverityLevel.NORMAL)
    public void checkMoreInfoLink() {
        Assert.assertTrue(page.isInfoLinkPresent(), "Ссылка «Подробнее о сервисе» не найдена");
    }

    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void checkConnectionForm() {
        performConnectionFormSteps();
    }

    @Test(dataProvider = "servicesData", priority = 5)
    @Severity(SeverityLevel.NORMAL)
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
    @Severity(SeverityLevel.CRITICAL)
    public void checkMobilePaymentDetailsInModal() {

        performConnectionFormSteps();


        page.waitForCardPopup();

        String orderAmount = page.getOrderAmount();
        Assert.assertTrue(orderAmount.contains("BYN"),
                "В описании заказа не найдена валюта BYN. Получено: '" + orderAmount + "'");

        String orderPhoneText = page.getOrderPhoneText();
        Assert.assertTrue(
                orderPhoneText.contains("375297777777") || orderPhoneText.contains("297777777"),
                "В описании заказа не найден ожидаемый номер телефона. Получено: '" + orderPhoneText + "'"
        );

        String payButtonText = page.getPayButtonText();
        Assert.assertTrue(payButtonText.contains("Оплатить"),
                "На кнопке оплаты отсутствует текст «Оплатить». Получено: '" + payButtonText + "'");
        Assert.assertTrue(payButtonText.contains("BYN"),
                "На кнопке оплаты не указана валюта BYN. Получено: '" + payButtonText + "'");

        Assert.assertEquals(page.getCardNumberLabel(), "Номер карты",
                "Неверная подпись для номера карты");
        Assert.assertEquals(page.getExpiryLabel(), "Срок действия",
                "Неверная подпись для срока действия");
        Assert.assertEquals(page.getCvcLabel(), "CVC",
                "Неверная подпись для CVC");

        Assert.assertTrue(page.arePaymentIconsVisible(),
                "Отсутствуют иконки платёжных систем в окне оплаты");

        page.enterCardNumber("4111111111111111");
        page.enterExpirationDate("12/25");
        page.enterCvc("123");
        page.enterHolderName("IVAN IVANOV");

        page.clickConfirmPaymentInIframe();
        page.switchBackToMainContent();

        System.out.println("Контекст возвращён в основной документ");
    }

    @DataProvider(name = "servicesData")
    public Object[][] provideServices() {
        return new Object[][]{
                {"Услуги связи", createLabels("Номер телефона", "", "Руб.", "E-mail для отправки чека")},
                {"Домашний интернет", createLabels("Номер абонента", "", "Руб.", "E-mail для отправки чека")},
                {"Рассрочка", createLabels("", "Номер счета на 44", "Руб.", "E-mail для отправки чека")},
                {"Задолженность", createLabels("", "Номер счета на 2073", "Руб.", "E-mail для отправки чека")}
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

    @Step("Заполнение формы пополнения: выбор услуги, ввод телефона и суммы, отправка")
    private void performConnectionFormSteps() {
        page.chooseService("Услуги связи");
        page.enterPhone("297777777");
        page.enterAmount("10");
        page.clickSubmit();
    }
}
