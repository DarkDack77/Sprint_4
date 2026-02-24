package ru.yandex.praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.praktikum.pages.MainPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest {

    private WebDriver driver;
    private MainPage mainPage;
    private final String question;
    private final String expectedAnswer;

    public FaqTest(String question, String expectedAnswer) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getFaqData() {
        return Arrays.asList(new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли."},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов!"}
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
    }

    @Test
    public void faqQuestionOpensCorrectAnswer() {
        mainPage.clickFaqQuestion(question);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        String pageText = driver.getPageSource();
        boolean hasAnswer = pageText.contains(expectedAnswer);

        assertTrue("Ответ не найден для вопроса: " + question, hasAnswer);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}