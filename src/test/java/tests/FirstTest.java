package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1280,800");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void openSauceDemo() {
        driver.get("https://www.saucedemo.com/");
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        Assert.assertEquals(title, "Swag Labs");
    }

    @Test
    public void validLoginTest() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String url = driver.getCurrentUrl();
        System.out.println("URL after login: " + url);
        Assert.assertTrue(url.contains("inventory"));
    }

    @Test
    public void invalidLoginShowsError() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        String error = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        System.out.println("Error shown: " + error);
        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}