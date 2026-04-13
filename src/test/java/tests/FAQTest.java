package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.HomePage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {
    private HomePage homePage;
    private int questionIndex;
    private String expectedAnswer;

    public FAQTest(int questionIndex, String expectedAnswer) {
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "Вопрос {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов ? один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    // Нормализация: удаляем невидимые символы, приводим к единому формату
    private String normalize(String s) {
        if (s == null) return "";
        // Заменяем неразрывные пробелы и другие спецсимволы на обычные
        return s.replace('\u00A0', ' ')
                .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "")
                .trim();
    }

    @Test
    public void checkFAQAnswers() {
        homePage = new HomePage(driver);
        homePage.clickQuestion(questionIndex);
        String actualAnswer = homePage.getAnswerText(questionIndex);
        String normalizedActual = normalize(actualAnswer);
        String normalizedExpected = normalize(expectedAnswer);
        assertEquals("Ответ на вопрос " + questionIndex + " не совпадает",
                normalizedExpected, normalizedActual);
    }
}