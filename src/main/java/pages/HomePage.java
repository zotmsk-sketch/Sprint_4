package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    // Локаторы (все в полях класса)
    private By topOrderButton = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");
    private By allOrderButtons = By.xpath("//button[text()='Заказать']"); // для поиска всех кнопок
    private By scooterLogo = By.xpath(".//img[@alt='Scooter']");
    private By yandexLogo = By.xpath(".//img[@alt='Yandex']");

    private By[] questionButtons = {
            By.id("accordion__heading-0"), By.id("accordion__heading-1"),
            By.id("accordion__heading-2"), By.id("accordion__heading-3"),
            By.id("accordion__heading-4"), By.id("accordion__heading-5"),
            By.id("accordion__heading-6"), By.id("accordion__heading-7")
    };

    private By[] answerPanels = {
            By.id("accordion__panel-0"), By.id("accordion__panel-1"),
            By.id("accordion__panel-2"), By.id("accordion__panel-3"),
            By.id("accordion__panel-4"), By.id("accordion__panel-5"),
            By.id("accordion__panel-6"), By.id("accordion__panel-7")
    };

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Метод для клика по нижней кнопке "Заказать"
    public void clickBottomOrderButton() {
        // Находим все кнопки "Заказать" на странице
        List<WebElement> buttons = driver.findElements(allOrderButtons);
        if (buttons.size() >= 2) {
            WebElement bottomButton = buttons.get(1); // вторая кнопка – нижняя
            // Прокручиваем к кнопке, чтобы она стала видимой
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bottomButton);
            bottomButton.click();
        } else {
            throw new RuntimeException("Не найдено две кнопки 'Заказать' на странице");
        }
    }

    public void clickQuestion(int index) {
        WebElement question = driver.findElement(questionButtons[index]);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        question.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(answerPanels[index]));
    }

    public String getAnswerText(int index) {
        return driver.findElement(answerPanels[index]).getText();
    }

    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }
}