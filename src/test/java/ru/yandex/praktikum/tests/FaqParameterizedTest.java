package ru.yandex.praktikum.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;  // Добавьте этот импорт
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pages.MainPage;
import java.time.Duration;

@RunWith(Parameterized.class)
public class FaqParameterizedTest extends BaseTest {
    private final int index;
    private final String expected;

    public FaqParameterizedTest(int index, String expected) {
        this.index = index;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getMainPageData() {
        return new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    @Test
    public void checkFaqAnswers() {
        System.out.println("\n=== ТЕСТ ВОПРОСА " + index + " ===");
        System.out.println("   Ожидаемый ответ: " + expected.substring(0, Math.min(30, expected.length())) + "...");

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.acceptCookies();
        mainPage.clickFaqQuestion(index);

        // Явное ожидание появления ответа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By answerLocator = By.id(String.format("accordion__panel-%d", index));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        String actual = mainPage.getFaqAnswerText(index);

        System.out.println("=== РЕЗУЛЬТАТ: " + (expected.equals(actual) ? "УСПЕХ" : "НЕУДАЧА") + " ===\n");
        Assert.assertEquals("Текст ответа не совпадает с ожидаемым для вопроса " + index, expected, actual);
    }
}