package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import java.util.Set;
import static org.junit.Assert.assertTrue;

public class ExtraTest {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePage.open();
    }

    @Test
    public void scooterLogoLeadsToHomePage() {
        homePage.clickTopOrderButton();
        homePage.clickScooterLogo();
        assertTrue("Не вернулись на главную",
                driver.getCurrentUrl().equals("https://qa-scooter.praktikum-services.ru/"));
    }

    @Test
    public void yandexLogoOpensNewWindow() {
        String originalWindow = driver.getWindowHandle();
        homePage.clickYandexLogo();
        Set<String> windows = driver.getWindowHandles();
        String newWindow = windows.stream().filter(w -> !w.equals(originalWindow)).findFirst().orElse(null);
        assertTrue("Новое окно не открылось", newWindow != null);
        driver.switchTo().window(newWindow);
        assertTrue("Не тот ресурс", driver.getCurrentUrl().contains("dzen.ru") || driver.getCurrentUrl().contains("yandex"));
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}