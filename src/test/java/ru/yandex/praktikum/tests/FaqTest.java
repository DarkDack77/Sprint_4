package ru.yandex.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.data.FaqData;
import ru.yandex.praktikum.pages.MainPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest extends BaseTest {

    private final FaqData faqData;

    public FaqTest(FaqData faqData) {
        this.faqData = faqData;
    }

    @Parameterized.Parameters
    public static Collection<FaqData> getFaqData() {
        return Arrays.asList(
                new FaqData("Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                new FaqData("Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат."),
                new FaqData("Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 12:00, а вы оплатили его в 13:00, аренда начнёт действовать с 13:00."),
                new FaqData("Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                new FaqData("Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку."),
                new FaqData("Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Зарядку не нужно возить — самокат заряжается от обычной розетки."),
                new FaqData("Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все свои."),
                new FaqData("Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов!")
        );
    }

    @Test
    public void faqQuestionOpensCorrectAnswer() {
        System.out.println("\n=== ТЕСТ ДЛЯ ВОПРОСА: " + faqData.getQuestion() + " ===");
        System.out.println("   Ожидаемый ответ: " + faqData.getExpectedAnswer().substring(0, Math.min(50, faqData.getExpectedAnswer().length())) + "...\n");

        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();

        mainPage.clickFaqQuestion(faqData.getQuestion());

        try { Thread.sleep(1500); } catch (InterruptedException e) {}

        boolean isVisible = mainPage.isAnswerVisible(faqData.getExpectedAnswer());

        System.out.println("\n=== РЕЗУЛЬТАТ: " + (isVisible ? "УСПЕХ" : "НЕУДАЧА") + " ===\n");

        assertTrue("Ответ не появился для вопроса: " + faqData.getQuestion(), isVisible);
    }
}