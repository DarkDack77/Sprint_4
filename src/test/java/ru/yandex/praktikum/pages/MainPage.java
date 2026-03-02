package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By COOKIE_BUTTON = By.id("rcc-confirm-button");
    private static final String FAQ_QUESTION_ID = "accordion__heading-%d";
    private static final String FAQ_ANSWER_ID = "accordion__panel-%d";
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage() {
        driver.get(PAGE_URL);
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(COOKIE_BUTTON)).click();
            System.out.println("   ✓ Куки приняты");
        } catch (Exception e) {
            System.out.println("   Баннер куки не найден");
        }
    }

    public void clickFaqQuestion(int index) {
        By faqQuestions = By.id(String.format(FAQ_QUESTION_ID, index));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(faqQuestions));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        System.out.println("   ✓ Вопрос " + index + " открыт");

        // НЕ используем Thread.sleep здесь
    }

    public String getFaqAnswerText(int index) {
        By faqAnswers = By.id(String.format(FAQ_ANSWER_ID, index));
        // Ждем видимости ответа перед получением текста
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(faqAnswers));
        String text = answerElement.getText();
        System.out.println("   Текст ответа: " + text.substring(0, Math.min(30, text.length())) + "...");
        return text;
    }
}