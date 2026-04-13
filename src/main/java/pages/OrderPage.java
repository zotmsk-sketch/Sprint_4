package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    // Статические локаторы (поля класса)
    private By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath(".//button[text()='Далее']");

    private By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-control");
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    private By confirmButton = By.xpath(".//button[text()='Да']");
    private By successMessage = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");

    // Локаторы для цвета (вынесены в поля)
    private By blackCheckbox = By.id("black");
    private By greyCheckbox = By.id("grey");

    // Приватные методы для динамических локаторов (чтобы не создавать By внутри методов)
    private By metroOption(String metroStation) {
        return By.xpath(".//div[text()='" + metroStation + "']");
    }

    private By rentalPeriodOption(String period) {
        return By.xpath(".//div[@class='Dropdown-option' and text()='" + period + "']");
    }

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillCustomerInfo(String name, String surname, String address, String metroStation, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(metroField).sendKeys(metroStation);

        WebElement metroElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(metroOption(metroStation)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroElement);
        metroElement.click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillRentalInfo(String date, String color, String comment) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(dateField));
        driver.findElement(dateField).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ESCAPE);

        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        new Actions(driver).moveToElement(dropdown).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(rentalPeriodOption("сутки"))).click();

        if (color.equals("black")) {
            driver.findElement(blackCheckbox).click();
        } else if (color.equals("grey")) {
            driver.findElement(greyCheckbox).click();
        }

        driver.findElement(commentField).sendKeys(comment);
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    public boolean isOrderSuccess() {
        WebElement messageElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        String actualText = messageElement.getText();
        // Проверка полного текста сообщения
        return "Заказ оформлен".equals(actualText);
    }
}