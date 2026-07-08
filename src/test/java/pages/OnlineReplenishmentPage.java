package pages;

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

    @FindBy(css = ".cards-brands img")
    private List<WebElement> paymentSystemIcons;

    public OnlineReplenishmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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


    public void switchToPaymentIframe() {
        By iframeLocator = By.cssSelector("iframe[src*='checkout.bepaid.by']");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
    }


    public void switchBackToMainContent() {
        driver.switchTo().defaultContent();
    }


    public void waitForCardPopup() {
        switchToPaymentIframe();

        // Ждём именно ID, который реально есть в HTML
        By cardInput = By.id("cc-number");
        wait.until(ExpectedConditions.elementToBeClickable(cardInput));
    }

    public void waitForCardPopup(Duration customTimeout) {
        WebDriverWait customWait = new WebDriverWait(driver, customTimeout);
        switchToPaymentIframe();
        By cardInput = By.id("cc-number");
        customWait.until(ExpectedConditions.elementToBeClickable(cardInput));
    }


    public void enterCardNumber(String cardNumber) {
        By cardInput = By.id("cc-number");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(cardInput));
        input.clear();
        input.sendKeys(cardNumber);
    }

    public void enterExpirationDate(String expiry) {
        By expInput = By.cssSelector("input[formcontrolname='expirationDate']");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(expInput));
        input.clear();
        input.sendKeys(expiry);
    }


    public void enterCvc(String cvc) {
        By cvcInput = By.cssSelector("input[formcontrolname='cvc']");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(cvcInput));
        input.clear();
        input.sendKeys(cvc);
    }


    public void enterHolderName(String name) {
        By holderInput = By.cssSelector("input[formcontrolname='holder']");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(holderInput));
        input.clear();
        input.sendKeys(name);
    }

    public void clickConfirmPaymentInIframe() {

        By payBtn = By.xpath(".//button[normalize-space()='Оплатить'] | .//span[normalize-space()='Оплатить']");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(payBtn));
        clickWithJs(btn);
    }

    public String getPayButtonText() {
        By payBtn = By.xpath(".//button[normalize-space()='Оплатить'] | .//span[normalize-space()='Оплатить']");
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(payBtn));
        return btn.getText().trim();
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


    public String getCardNumberPlaceholder() {
        switchToPaymentIframe();
        By input = By.id("cc-number");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        return el.getAttribute("placeholder");
    }

    public String getExpiryPlaceholder() {
        switchToPaymentIframe();
        By input = By.cssSelector("input[formcontrolname='expirationDate']");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        return el.getAttribute("placeholder");
    }

    public String getCvcPlaceholder() {
        switchToPaymentIframe();
        By input = By.cssSelector("input[formcontrolname='cvc']");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(input));
        return el.getAttribute("placeholder");
    }

    public String getCardNumberLabel() {
        switchToPaymentIframe();
        WebElement label = driver.findElement(By.cssSelector("input[id='cc-number'] + label"));
        return label.getText().trim();
    }

    public String getExpiryLabel() {
        switchToPaymentIframe();
        WebElement label = driver.findElement(By.cssSelector("input[formcontrolname='expirationDate'] + label"));
        return label.getText().trim();
    }

    public String getCvcLabel() {
        switchToPaymentIframe();
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
