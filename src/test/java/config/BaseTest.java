package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    @Getter
    protected WebDriverWait explicitWait;
    protected static final String BASE_URL = "https://www.mts.by";

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("--headless=new"); // раскомментируй, если нужен headless
        options.addArguments("--log-level=3");

        driver = new ChromeDriver(options);
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.get(BASE_URL);
        acceptMtsCookies(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected void acceptMtsCookies(WebDriver driver) {
        By bannerLocator = By.cssSelector(".cookie__wrapper");

        if (driver.findElements(bannerLocator).isEmpty()) {
            System.out.println("[COOKIES] Баннер не обнаружен — пропускаем.");
            return;
        }

        By acceptButtonLocator = By.id("cookie-agree");

        try {
            WebElement acceptBtn = explicitWait.until(
                    ExpectedConditions.elementToBeClickable(acceptButtonLocator)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", acceptBtn
            );

            explicitWait.until(ExpectedConditions.visibilityOf(acceptBtn));
            acceptBtn.click();

            System.out.println("[COOKIES] Нажали «Принять». Ждём исчезновения баннера");
            explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(bannerLocator));
            System.out.println("[COOKIES] Баннер закрыт");
        } catch (Exception e) {
            System.out.println("[COOKIES] Не удалось закрыть баннер: " + e.getMessage());
        }
    }
}
