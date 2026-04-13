package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.OrderPage;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private HomePage homePage;
    private OrderPage orderPage;

    private String name;
    private String surname;
    private String address;
    private String metro;
    private String phone;
    private String date;
    private String color;
    private String comment;

    public OrderTest(String name, String surname, String address, String metro, String phone,
                     String date, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Петров", "ул. Ленина, 1", "Сокольники", "89991234567",
                        "25.12.2024", "black", "Позвонить за час"},
                {"Мария", "Иванова", "пр. Мира, 10", "Китай-город", "89161234567",
                        "26.12.2024", "grey", "Домофон 123"}
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        orderPage = new OrderPage(driver);
        homePage.open();
    }

    @Test
    public void createOrder() {
        homePage.clickTopOrderButton();
        orderPage.fillCustomerInfo(name, surname, address, metro, phone);
        orderPage.fillRentalInfo(date, color, comment);
        orderPage.confirmOrder();
        assertTrue("Заказ не был оформлен", orderPage.isOrderSuccess());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
//