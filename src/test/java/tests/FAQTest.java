package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class FAQTest {
    private WebDriver driver;
    private HomePage homePage;
    private int questionIndex;

    public FAQTest(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{0},{1},{2},{3},{4},{5},{6},{7}});
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePage.open();
    }

    @Test
    public void checkFAQAnswers() {
        homePage.clickQuestion(questionIndex);
        String actualAnswer = homePage.getAnswerText(questionIndex);
        assertFalse("Ответ на вопрос " + questionIndex + " пустой", actualAnswer.isEmpty());
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}