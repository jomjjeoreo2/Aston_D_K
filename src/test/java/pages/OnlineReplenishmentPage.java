package pages;

import config.BaseTest;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineReplenishmentPage {

    public static final Map<String, List<String>> FIELDS_PER_SERVICE = new HashMap<>();
    static {
        FIELDS_PER_SERVICE.put("Услуги связи", List.of("phone", "sum", "email"));
        FIELDS_PER_SERVICE.put("Домашний интернет", List.of("phone", "sum", "email"));
        FIELDS_PER_SERVICE.put("Рассрочка", List.of("account", "sum", "email"));
        FIELDS_PER_SERVICE.put("Задолженность", List.of("account", "sum", "email"));
    }

    private static final Map<String, String> ACCOUNT_FIELD_IDS = new HashMap<>();
    static {
        ACCOUNT_FIELD_IDS.put("Рассрочка", "score-instalment");
        ACCOUNT_FIELD_IDS.put("Задолженность", "score-arrears");
    }

    private final By currencyLabelLocator = By.cssSelector("label[for='connection-sum'], label[for='score-instalment'], label[for='score-arrears']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".pay__wrapper h2")
    private WebElement blockTitle;

    @FindBy(id = "pay")
    private WebElement serviceSelector;

    @FindBy(css = "[id$='phone']")
    private WebElement phoneField;

    @FindBy(css = "[id$='sum']")
    private WebElement amountField;

    @FindBy(css = "[id$='email']")
    private WebElement emailField;

    @FindBy(xpath = ".//button[normalize-space() = 'Продолжить']")
    private WebElement submitBtn;

    @FindBy(linkText = "Подробнее о сервисе")
    private WebElement infoLink;

    @FindBy(className = "pay__partners")
    private WebElement partnersSection;

    @FindBy(css = "#card-number")
    private WebElement cardNumberInput;

    @FindBy(css = "#card-expiry")
    private WebElement expiryDateInput;

    @FindBy(css = "#card-cvc")
    private WebElement cvcCodeInput;

    @FindBy(xpath = "//button[text()='Оплатить']")
    private WebElement payButtonInModal;

    @FindBy(css = ".payment-system-icons img")
    private List<WebElement> paymentSystemIcons;

    public OnlineReplenishmentPage(WebDriver driver, BaseTest baseTest) {
        this.driver = driver;

        this.wait = baseTest.getExplicitWait() != null ? baseTest.getExplicitWait() : new WebDriverWait(driver, Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }

    public void waitUntilBlockIsReady() {
        wait.until(ExpectedConditions.visibilityOf(blockTitle));
    }


    public void waitForCurrencyText(String expectedText) {

        By locator = By.cssSelector("label[for*='sum']");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public String getAmountLabel() {

        waitForCurrencyText("Руб.");

        List<WebElement> labels = driver.findElements(By.cssSelector("label[for*='sum']"));
        if (labels.isEmpty()) {
            throw new NoSuchElementException("Не найден label для поля суммы после ожидания");
        }
        return labels.get(0).getText().trim();
    }

    public String getBlockTitle() {
        return blockTitle.getText();
    }

    public void chooseService(String option) {
        By headerLocator = By.cssSelector(".select__header");
        wait.until(ExpectedConditions.elementToBeClickable(headerLocator)).click();

        String escapedOption = option.replace("'", "\\'");
        By optionLocator = By.xpath("//p[normalize-space(.)='" + escapedOption + "']");

        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        optionElement.click();

        By activeLocator = By.xpath("//li[contains(@class, 'select__item active')]//p[normalize-space(.)='" + escapedOption + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(activeLocator));

        String containerId = getContainerIdByOption(option);
        By containerLocator = By.id(containerId);

        wait.until(drv -> {
            try {
                WebElement container = drv.findElement(containerLocator);
                return container.isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });

        waitForCurrencyText("Руб.");
    }

    public void enterPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void enterAmount(String amount) {
        amountField.clear();
        amountField.sendKeys(amount);
    }

    public void clickSubmit() {
        submitBtn.click();
    }

    public boolean hasPartnersLogos() {
        List<WebElement> images = partnersSection.findElements(By.tagName("img"));
        return !images.isEmpty();
    }

    public boolean isInfoLinkPresent() {
        return infoLink.isDisplayed();
    }

    public String getPhonePlaceholder() {
        return phoneField.getAttribute("placeholder");
    }

    public String getEmailPlaceholder() {
        return emailField.getAttribute("placeholder");
    }

    public void waitForCardPopup(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(cardNumberInput));
    }

    public String getPayButtonText() {
        return payButtonInModal.getText();
    }

    public boolean arePaymentIconsVisible() {
        if (paymentSystemIcons.size() < 4) {
            return false;
        }
        for (WebElement icon : paymentSystemIcons) {
            if (!icon.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public String getCardNumberPlaceholder() {
        return cardNumberInput.getAttribute("placeholder");
    }

    public String getExpiryPlaceholder() {
        return expiryDateInput.getAttribute("placeholder");
    }

    public String getCvcPlaceholder() {
        return cvcCodeInput.getAttribute("placeholder");
    }

    public String getAccountPlaceholder() {
        String currentService = getCurrentService();
        String fieldId = ACCOUNT_FIELD_IDS.get(currentService);

        if (fieldId == null) {
            throw new IllegalArgumentException("Для услуги '" + currentService + "' не определён ID поля счёта. Добавь в ACCOUNT_FIELD_IDS.");
        }

        By locator = By.id(fieldId);
        WebElement scoreField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return scoreField.getAttribute("placeholder");
    }

    private String getCurrentService() {
        By activeOptionLocator = By.cssSelector(".select__item.active .select__option");
        WebElement activeOption = driver.findElement(activeOptionLocator);
        return activeOption.getText().trim();
    }

    private String getContainerIdByOption(String option) {
        if ("Услуги связи".equals(option)) {
            return "pay-connection";
        } else if ("Домашний интернет".equals(option)) {
            return "pay-internet";
        } else if ("Рассрочка".equals(option)) {
            return "pay-installment";
        } else if ("Задолженность".equals(option)) {
            return "pay-arrears";
        } else {
            throw new IllegalArgumentException("Неизвестная услуга: " + option);
        }
    }
}
