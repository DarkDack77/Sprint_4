package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage extends BasePage {

    private By cookieButton = By.cssSelector("#rcc-confirm-button");
    private By topOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[1]");
    private By bottomOrderButton = By.xpath("(//button[contains(text(), 'Заказать')])[2]");

    // Локаторы для FAQ
    private By faqHeading = By.className("accordion__heading");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookieBanner() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBtn.click();
            System.out.println("   ✓ Баннер куки закрыт");
        } catch (Exception e) {
            System.out.println("   Баннер куки не найден или уже закрыт");
        }
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public void clickTopOrderButton() {
        WebElement button = waitForElementClickable(topOrderButton, 5);
        button.click();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void clickBottomOrderButton() {
        WebElement button = waitForElementClickable(bottomOrderButton, 5);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        button.click();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    // ========== МЕТОДЫ ДЛЯ FAQ ==========

    public void clickFaqQuestion(String questionText) {
        System.out.println("   Ищем вопрос: " + questionText);

        try {
            // Находим все заголовки FAQ
            List<WebElement> headings = driver.findElements(faqHeading);

            for (int i = 0; i < headings.size(); i++) {
                WebElement heading = headings.get(i);
                String headingText = heading.getText();

                // Проверяем, содержит ли заголовок искомый текст
                if (headingText.contains(questionText.substring(0, Math.min(15, questionText.length())))) {
                    System.out.println("   Найден вопрос " + i + ": " + headingText);

                    // Скроллим к вопросу
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", heading);
                    try { Thread.sleep(500); } catch (InterruptedException e) {}

                    // Кликаем по родительскому элементу (кнопке)
                    WebElement parentButton = heading.findElement(By.xpath("./parent::div"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentButton);

                    System.out.println("   ✓ Вопрос открыт");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    return;
                }
            }

            System.out.println("   ✗ Вопрос не найден");

        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при клике на вопрос: " + e.getMessage());
        }
    }

    // Обычный метод поиска ответа
    public boolean isAnswerVisible(String expectedAnswer) {
        System.out.println("   Проверяем ответ обычным способом...");

        try {
            Thread.sleep(1500);

            String searchText = expectedAnswer.length() > 20 ?
                    expectedAnswer.substring(0, 20) : expectedAnswer;

            String xpath = String.format("//*[contains(text(), '%s')]", searchText);
            List<WebElement> elements = driver.findElements(By.xpath(xpath));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("   ✓ Ответ найден обычным способом");
                    return true;
                }
            }

            // Если обычный способ не сработал, пробуем JavaScript
            return isAnswerVisibleJS(expectedAnswer);

        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при обычном поиске: " + e.getMessage());
            return isAnswerVisibleJS(expectedAnswer);
        }
    }

    // JavaScript метод поиска ответа
    public boolean isAnswerVisibleJS(String expectedAnswer) {
        System.out.println("   Проверяем ответ через JavaScript...");

        try {
            Thread.sleep(1000);

            String script =
                    "var elements = document.querySelectorAll('*');" +
                            "for(var i = 0; i < elements.length; i++) {" +
                            "  if(elements[i].textContent && elements[i].textContent.includes('" + expectedAnswer.substring(0, 20) + "')) {" +
                            "    return true;" +
                            "  }" +
                            "}" +
                            "return false;";

            boolean found = (boolean) ((JavascriptExecutor) driver).executeScript(script);
            System.out.println("   Результат JavaScript поиска: " + found);
            return found;

        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при JavaScript поиске: " + e.getMessage());
            return false;
        }
    }
}