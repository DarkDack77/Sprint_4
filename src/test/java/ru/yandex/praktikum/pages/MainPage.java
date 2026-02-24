package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    private By cookieButton = By.cssSelector("#rcc-confirm-button");
    private By topOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[1]");
    private By bottomOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[2]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookieBanner() {
        try {
            waitForElementClickable(cookieButton, 10).click();
        } catch (Exception e) {}
    }

    public void clickTopOrderButton() {
        waitForElementClickable(topOrderButton, 10).click();
    }

    public void clickBottomOrderButton() {
        waitForElementClickable(bottomOrderButton, 10).click();
    }

    // ✅ ИСПРАВЛЕНО: скрываем картинку + явные ожидания + клик через JS
    public void clickFaqQuestion(String questionText) {
        // 1. Скрываем картинку самоката
        ((JavascriptExecutor) driver).executeScript(
                "var img = document.querySelector('img[src*=\"scooter.png\"]'); if(img) img.remove();"
        );

        // 2. Находим вопрос
        String xpath = String.format("//div[contains(@class, 'accordion__button') and contains(text(), '%s')]", questionText);
        WebElement question = waitForElementClickable(By.xpath(xpath), 10);

        // 3. Скроллим к вопросу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", question);

        // 4. Кликаем через JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }

    // ✅ ИСПРАВЛЕНО: проверка ответа с явным ожиданием
    public boolean isAnswerVisible(String expectedAnswer) {
        String xpath = String.format("//*[contains(text(), '%s')]", expectedAnswer);
        try {
            waitForElementVisible(By.xpath(xpath), 10);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}