package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.HomePage;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {
    private HomePage homePage;
    private int questionIndex;
    private List<String> expectedKeywords;

    public FAQTest(int questionIndex, List<String> expectedKeywords) {
        this.questionIndex = questionIndex;
        this.expectedKeywords = expectedKeywords;
    }

    @Parameterized.Parameters(name = "Вопрос {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, Arrays.asList("Сутки — 400 рублей", "Оплата курьеру — наличными или картой")},
                {1, Arrays.asList("несколько заказов")},
                {2, Arrays.asList("отсчёт времени аренды", "оплатите заказ курьеру")},
                {3, Arrays.asList("начиная с завтрашнего дня")},
                {4, Arrays.asList("позвонить в поддержку", "1010")},
                {5, Arrays.asList("полной зарядкой", "восемь суток")},
                {6, Arrays.asList("Штрафа не будет")},
                {7, Arrays.asList("Всем самокатов")}
        });
    }

    private String normalize(String s) {
        if (s == null) return "";
        // Приводим к нижнему регистру, оставляем только буквы, цифры и пробелы
        return s.toLowerCase().replaceAll("[^\\p{L}\\p{N}\\s]", "");
    }

    @Test
    public void checkFAQAnswers() {
        homePage = new HomePage(driver);
        homePage.clickQuestion(questionIndex);
        String actualAnswer = homePage.getAnswerText(questionIndex);
        String normalizedActual = normalize(actualAnswer);
        for (String keyword : expectedKeywords) {
            String normalizedKeyword = normalize(keyword);
            assertTrue("Ответ на вопрос " + questionIndex + " не содержит ключевой фразы: " + keyword,
                    normalizedActual.contains(normalizedKeyword));
        }
    }
}