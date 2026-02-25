package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    private By cookieButton = By.cssSelector("#rcc-confirm-button");
    private By topOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[1]");
    private By bottomOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[2]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookieBanner() {
        try {
            if (driver.findElements(cookieButton).size() > 0) {
                waitForElementClickable(cookieButton, 5);
                findElement(cookieButton).click();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // Игнорируем
        }
    }

    public void clickTopOrderButton() {
        waitForElementClickable(topOrderButton, 5);
        findElement(topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        waitForElementClickable(bottomOrderButton, 5);
        findElement(bottomOrderButton).click();
    }

    public void clickFaqQuestion(String questionText) {
        try {
            System.out.println("   Поиск вопроса: " + questionText.substring(0, Math.min(20, questionText.length())) + "...");

            // Удаляем мешающее изображение
            ((JavascriptExecutor) driver).executeScript(
                    "var img = document.querySelector('img[src*=\"scooter.png\"]'); if(img) img.remove();"
            );

            // Самый простой и надежный способ - найти все вопросы и кликнуть по нужному
            String script =
                    "var questions = document.querySelectorAll('.accordion__heading');" +
                            "for(var i = 0; i < questions.length; i++) {" +
                            "  var text = questions[i].textContent || questions[i].innerText;" +
                            "  if(text.includes('" + questionText.substring(0, 15) + "')) {" +
                            "    questions[i].parentNode.scrollIntoView({block: 'center'});" +
                            "    setTimeout(function() { questions[i].parentNode.click(); }, 100);" +
                            "    return true;" +
                            "  }" +
                            "}" +
                            "return false;";

            boolean clicked = (boolean) ((JavascriptExecutor) driver).executeScript(script);
            System.out.println("   JavaScript клик выполнен: " + clicked);

            Thread.sleep(1500); // Ждем открытия

        } catch (Exception e) {
            System.out.println("   Ошибка при клике на вопрос: " + e.getMessage());
        }
    }

    public boolean isAnswerVisible(String expectedAnswer) {
        try {
            Thread.sleep(500);

            System.out.println("   Поиск ответа: " + expectedAnswer.substring(0, Math.min(30, expectedAnswer.length())) + "...");

            // Ищем текст ответа на странице
            String script =
                    "var elements = document.querySelectorAll('.accordion__panel p, .accordion__panel div');" +
                            "for(var i = 0; i < elements.length; i++) {" +
                            "  var text = elements[i].textContent || elements[i].innerText;" +
                            "  if(text.includes('" + expectedAnswer.substring(0, 20) + "')) {" +
                            "    return true;" +
                            "  }" +
                            "}" +
                            "return false;";

            boolean found = (boolean) ((JavascriptExecutor) driver).executeScript(script);
            System.out.println("   Ответ найден: " + found);

            return found;

        } catch (Exception e) {
            System.out.println("   Ошибка при поиске ответа: " + e.getMessage());
            return false;
        }
    }
}