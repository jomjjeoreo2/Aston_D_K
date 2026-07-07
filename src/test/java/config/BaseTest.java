package config;

import static org.testng.Assert.fail;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait explicitWait;

    @BeforeClass
    public void setup() {
        try {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            driver = new ChromeDriver(options);

            driver.manage().window().maximize();


            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

            driver.get("https://mts.by");
            explicitWait.until(ExpectedConditions.urlContains("mts.by"));
            closeCookies();
        } catch (Exception e) {
            fail("Ошибка при инициализации браузера: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


    protected void closeCookies() {
        By bannerLocator = By.cssSelector(".cookie");
        WebElement banner = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));

        By acceptBtnLocator = By.id("cookie-agree");
        WebElement acceptBtn = banner.findElement(acceptBtnLocator);
        acceptBtn.click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(bannerLocator));
    }

    public WebDriverWait getExplicitWait() {
        return explicitWait;
    }
}