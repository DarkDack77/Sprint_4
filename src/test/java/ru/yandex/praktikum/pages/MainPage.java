package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String url = "https://qa-scooter.praktikum-services.ru/";

    private final By cookieButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage() {
        driver.get(url);
    }

    public void acceptCookies() {
        WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
        cookieBtn.click();
    }

    public void clickFaqQuestion(int index) {
        By questionLocator = By.id("accordion__heading-" + index);
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        question.click();
    }

    public String getFaqAnswerText(int index) {
        By answerLocator = By.id("accordion__panel-" + index);
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText();
    }
}