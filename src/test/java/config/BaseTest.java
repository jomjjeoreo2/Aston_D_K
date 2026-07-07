package config;

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
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options);

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.get("https://mts.by");
        explicitWait.until(ExpectedConditions.urlContains("mts.by"));

        closeCookies();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void closeCookies() {
        try {
            By bannerLocator = By.cssSelector(".cookie.show");
            WebElement banner = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));

            By acceptBtnLocator = By.cssSelector(".cookie__ok[id=cookie-agree]");
            WebElement acceptBtn = banner.findElement(acceptBtnLocator);
            acceptBtn.click();

            explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(bannerLocator));
        } catch (TimeoutException e) {
            System.out.println("Cookie-баннер не найден или уже закрыт — продолжаем тест.");
        } catch (NoSuchElementException e) {
            System.out.println("Cookie-баннер не найден (NoSuchElement) — продолжаем тест.");
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка при закрытии cookie-баннера: " + e.getMessage());
        }
    }

    public WebDriverWait getExplicitWait() {
        return explicitWait;
    }
}
