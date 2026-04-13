package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StatusPage {
    private WebDriver driver;
    private By notFoundMessage = By.xpath(".//div[text()='Такого заказа нет']");

    public StatusPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isNotFoundDisplayed() {
        return driver.findElement(notFoundMessage).isDisplayed();
    }
}