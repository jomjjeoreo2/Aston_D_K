package config;

import static org.testng.Assert.fail;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        try {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://mts.by");
            Thread.sleep(3000);
        } catch (Exception e) {
            fail("Ошибка при открытии браузера: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void closeCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cookie")));
        WebElement acceptBtn = banner.findElement(By.id("cookie-agree"));
        acceptBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(banner));
    }
}