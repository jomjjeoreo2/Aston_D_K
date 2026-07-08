package pages;

import config.BaseTest;
import org.openqa.selenium.*;
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

    @FindBy(css = "input[formcontrolname='creditCard']")
    private WebElement cardNumberInput;

    @FindBy(css = "input[formcontrolname='expirationDate']")
    private WebElement expiryDateInput;

    @FindBy(css = "input[formcontrolname='cvc']")
    private WebElement cvcCodeInput;

    @FindBy(xpath = "//button[text()='Оплатить']")
    private WebElement payButtonInModal;

    @FindBy(css = ".cards-brands img")
    private List<WebElement> paymentSystemIcons;

    public OnlineReplenishmentPage(WebDriver driver, BaseTest baseTest) {
        this.driver = driver;
        this.wait = baseTest.getExplicitWait();
        PageFactory.initElements(driver, this);
    }

    public void waitUntilBlockIsReady() {
        wait.until(ExpectedConditions.visibilityOf(blockTitle));
    }

    public void waitForCurrencyText(String expectedText) {
        By locator = By.cssSelector("label[for*='sum']");
        WebElement label = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(d -> label.getText().contains(expectedText));
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
        ensureCookiesAreAccepted();

        By headerLocator = By.cssSelector(".select__header");
        WebElement header = wait.until(ExpectedConditions.elementToBeClickable(headerLocator));
        scrollIntoView(header);
        clickWithJs(header);

        String escapedOption = option.replace("'", "\\'");
        By optionLocator = By.xpath("//p[normalize-space() = '" + escapedOption + "']");

        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        scrollIntoView(optionElement);
        clickWithJs(optionElement);

        String containerId = getContainerIdByOption(option);
        By containerLocator = By.id(containerId);
        wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator));

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
        scrollIntoView(submitBtn);
        clickWithJs(submitBtn);
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

    public void waitForCardPopup() {
        wait.until(ExpectedConditions.visibilityOf(cardNumberInput));
    }

    public void waitForCardPopup(Duration customTimeout) {
        WebDriverWait customWait = new WebDriverWait(driver, customTimeout);
        customWait.until(ExpectedConditions.visibilityOf(cardNumberInput));
    }

    public String getPayButtonText() {
        return payButtonInModal.getText().trim();
    }

    public String getOrderPhoneText() {
        WebElement textEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pay-description__text"))
        );
        return textEl.getText().trim();
    }

    public boolean arePaymentIconsVisible() {
        List<WebElement> icons = driver.findElements(By.cssSelector(".cards-brands img"));
        if (icons.isEmpty()) {
            return false;
        }
        for (WebElement icon : icons) {
            if (!icon.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    // оставлю для совместимости, но в дом лейблы
    public String getCardNumberPlaceholder() {
        return cardNumberInput.getAttribute("placeholder");
    }

    public String getExpiryPlaceholder() {
        return expiryDateInput.getAttribute("placeholder");
    }

    public String getCvcPlaceholder() {
        return cvcCodeInput.getAttribute("placeholder");
    }

    public String getCardNumberLabel() {
        // input[formcontrolname='creditCard'] + label
        WebElement label = driver.findElement(By.cssSelector("input[formcontrolname='creditCard'] + label"));
        return label.getText().trim();
    }

    public String getExpiryLabel() {
        // input[formcontrolname='expirationDate'] + label
        WebElement label = driver.findElement(By.cssSelector("input[formcontrolname='expirationDate'] + label"));
        return label.getText().trim();
    }

    public String getCvcLabel() {
        // input[formcontrolname='cvc'] + label
        WebElement label = driver.findElement(By.cssSelector("input[formcontrolname='cvc'] + label"));
        return label.getText().trim();
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

    public String getOrderAmount() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".pay-description__cost span")
        ));
        return el.getText().trim();
    }


    private void ensureCookiesAreAccepted() {
        By bannerLocator = By.cssSelector(".cookie__wrapper");
        List<WebElement> banners = driver.findElements(bannerLocator);
        if (banners.isEmpty()) {
            return;
        }

        try {
            By acceptBtnLocator = By.id("cookie-agree");
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(acceptBtnLocator));

            scrollIntoView(btn);
            Thread.sleep(200);
            btn.click();

            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(bannerLocator));
            System.out.println("[COOKIES] Баннер успешно закрыт.");
        } catch (Exception e) {
            System.out.println("[COOKIES] Не удалось закрыть баннер (возможно, его нет или структура изменилась): " + e.getMessage());
        }
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

    private void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void clickWithJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
}