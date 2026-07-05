package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OnlineReplenishmentPage {

    private WebDriver driver;

    @FindBy(css = ".pay__wrapper h2")
    private WebElement blockTitle;

    @FindBy(id = "pay")
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

    public OnlineReplenishmentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getBlockTitle() {
        return blockTitle.getText();
    }

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

}