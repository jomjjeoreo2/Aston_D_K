package pages;

import config.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class OnlineReplenishmentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /* --- Элементы верхнего уровня (твои рабочие) --- */

    @FindBy(css = ".pay__wrapper h2")
    private WebElement blockTitle;

    @FindBy(id = "pay") // Обычный HTML-select
    private WebElement serviceSelector;

    @FindBy(css = "[id$=phone]")
    private WebElement phoneField;

    @FindBy(css = "[id$=sum]")
    private WebElement amountField;

    @FindBy(css = "[id$=email]")
    private WebElement emailField;

    @FindBy(xpath = ".//button[normalize-space() = 'Продолжить']")
    private WebElement submitBtn;

    @FindBy(linkText = "Подробнее о сервисе")
    private WebElement infoLink;

    @FindBy(className = "pay__partners")
    private WebElement partnersSection;

    /* --- Элементы модального окна (добавляю для Req #2) --- */

    // Карточные поля появляются в модальном окне
    @FindBy(id = "card-number")
    private WebElement cardNumberInput;

    @FindBy(id = "card-expiry")
    private WebElement expiryDateInput;

    @FindBy(id = "card-cvc")
    private WebElement cvcCodeInput;

    // Кнопка «Оплатить» в модальном окне
    @FindBy(xpath = "//button[text()='Оплатить']")
    private WebElement payButtonInModal;

    // Иконки платежных систем в модальном окне
    @FindBy(css = ".payment-system-icons img")
    private List<WebElement> paymentSystemIcons;

    /* --- Конструктор --- */

    public OnlineReplenishmentPage(WebDriver driver, BaseTest baseTest) {
        this.driver = driver;
        this.wait = baseTest.getExplicitWait(); // Присваиваем внутри конструктора
        PageFactory.initElements(driver, this);
    }

    public void waitUntilBlockIsReady() {
        wait.until(ExpectedConditions.visibilityOf(blockTitle));
    }

    /* --- Методы верхнего уровня (твои рабочие) --- */

    public String getBlockTitle() {
        return blockTitle.getText();
    }

    // ✅ Можно оставить твой старый метод
    public void chooseService(String option) {
        new Select(serviceSelector).selectByValue(option);
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
        return !partnersSection.findElements(By.tagName("img")).isEmpty();
    }

    public boolean isInfoLinkPresent() {
        return infoLink.isDisplayed();
    }

    /* --- Методы для requirement #1 (надписи в пустых полях) --- */

    // ✅ Проверка текущих полей
    public String getPhonePlaceholder() {
        return phoneField.getAttribute("placeholder");
    }

    public String getAmountLabel() {
        return driver.findElement(By.xpath("//label[contains(@for, '-sum')]")).getText();
    }

    public String getEmailPlaceholder() {
        return emailField.getAttribute("placeholder");
    }

    /* --- Методы для requirement #2 (модальное окно) --- */

    public void waitForCardPopup(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(cardNumberInput));
    }

    public String getPayButtonText() {
        return payButtonInModal.getText();
    }

    public boolean arePaymentIconsVisible() {
        return paymentSystemIcons.size() >= 4 && paymentSystemIcons.stream().allMatch(WebElement::isDisplayed);
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
}