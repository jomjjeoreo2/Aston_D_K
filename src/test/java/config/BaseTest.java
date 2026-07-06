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
    protected WebDriverWait explicitWait; // Доступно для всех тестов

    @BeforeClass
    public void setup() {
        try {
            WebDriverManager.chromedriver().setup();

            // ✅ Включаем headless mode
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new"); // Новый флаг для Chromium 10+
            driver = new ChromeDriver(options);

            driver.manage().window().maximize();

            // Создаем общий таймаут ожидания
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Переходим на сайт и ждем, пока он загрузится
            driver.get("https://mts.by");
            explicitWait.until(ExpectedConditions.urlContains("mts.by")); // Проверка URL

            // Закрываем куки-баннер
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

    /* === Метод для закрытия куки === */

    protected void closeCookies() {
        try {
            // Ждем появления баннера
            By bannerLocator = By.cssSelector(".cookie");
            WebElement banner = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));

            // Находим кнопку согласия
            By acceptBtnLocator = By.id("cookie-agree");
            WebElement acceptBtn = banner.findElement(acceptBtnLocator);
            acceptBtn.click();

            // Ждем, пока баннер исчезнет
            explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(bannerLocator));
        } catch (TimeoutException ignored) {
            // Если баннера нет — ничего страшного
        }
    }

    /* === Публичный геттер === */

    public WebDriverWait getExplicitWait() {
        return explicitWait;
    }
}