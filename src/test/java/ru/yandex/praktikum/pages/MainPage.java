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
        try { findElement(cookieButton).click(); } catch (Exception e) {}
    }

    public void clickTopOrderButton() { findElement(topOrderButton).click(); }
    public void clickBottomOrderButton() { findElement(bottomOrderButton).click(); }

    // ✅ ИСПРАВЛЕНИЕ: скрываем картинку + скролл + JS-клик
    public void clickFaqQuestion(String questionText) {
        // 1. Скрываем картинку самоката (главная проблема!)
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var img = document.querySelector('img[src*=\"scooter.png\"]'); if(img) img.style.display='none';"
            );
        } catch (Exception e) {}

        // 2. Находим вопрос
        String xpath = String.format("//div[contains(@class, 'accordion__button') and contains(text(), '%s')]", questionText);
        WebElement question = findElement(By.xpath(xpath));

        // 3. Скроллим к вопросу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", question);
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // 4. Кликаем через JavaScript (обходит картинку)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }

    // ✅ Проверка ответа
    public boolean isAnswerVisible(String expectedAnswer) {
        try {
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            String xpath = String.format("//*[contains(text(), '%s')]", expectedAnswer);
            WebElement answer = findElement(By.xpath(xpath));
            return answer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}