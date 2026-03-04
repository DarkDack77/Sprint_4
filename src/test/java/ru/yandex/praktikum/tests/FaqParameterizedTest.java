package ru.yandex.praktikum.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.pages.MainPage;

@RunWith(Parameterized.class)
public class FaqParameterizedTest extends BaseTest {

    private final String questionText;
    private final String expectedAnswer;

    public FaqParameterizedTest(String questionText, String expectedAnswer) {
        this.questionText = questionText;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "FAQ: {0}")
    public static Object[][] data() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."}
        };
    }

    @Test
    public void faqAnswerShouldMatch() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        mainPage.clickFaqQuestion(questionText);
        String actual = mainPage.getFaqAnswerText(questionText);

        Assert.assertEquals("Ответ для вопроса не совпал: " + questionText, expectedAnswer, actual);
    }
}