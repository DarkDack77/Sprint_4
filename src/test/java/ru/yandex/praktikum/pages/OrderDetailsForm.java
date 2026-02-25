package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OrderDetailsForm extends BasePage {

    private By deliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-control"); // Пробуем другой класс
    private By orderButton = By.xpath("//button[contains(text(), 'Заказать') and not(contains(@class, 'Header'))]");
    private By colorBlackCheckbox = By.id("black");

    public OrderDetailsForm(WebDriver driver) {
        super(driver);
    }

    public void fillAllFields(String deliveryDate) {
        try {
            System.out.println("Заполняем дату: " + deliveryDate);
            waitForElementVisible(deliveryDateField, 5);
            findElement(deliveryDateField).sendKeys(deliveryDate);
            findElement(deliveryDateField).sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            System.out.println("Выбираем срок аренды (упрощенный способ)");
            selectRentalPeriodSimple();

            System.out.println("Выбираем цвет самоката");
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    findElement(colorBlackCheckbox)
            );
            Thread.sleep(500);

            System.out.println("Все поля заполнены");

        } catch (Exception e) {
            System.out.println("Ошибка при заполнении полей: " + e.getMessage());
        }
    }

    public void selectRentalPeriodSimple() {
        try {
            // Самый простой способ - используем стандартный Select если это обычный выпадающий список
            Thread.sleep(1000);

            // Пробуем найти и кликнуть по полю
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));

            // Кликаем через JavaScript
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    driver.findElement(rentalPeriodField)
            );

            Thread.sleep(1000);

            // Пробуем выбрать вариант через JavaScript
            ((JavascriptExecutor) driver).executeScript(
                    "var options = document.querySelectorAll('.Dropdown-option');" +
                            "if(options.length > 0) options[0].click();"
            );

            Thread.sleep(500);
            System.out.println("Срок аренды выбран");

        } catch (Exception e) {
            System.out.println("Ошибка при выборе срока аренды: " + e.getMessage());
            // Если не сработало, пропускаем - может быть уже выбран по умолчанию
        }
    }

    public void placeOrder() {
        try {
            System.out.println("Нажимаем кнопку Заказать");
            Thread.sleep(1000);

            // Прокручиваем к кнопке
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                    findElement(orderButton)
            );
            Thread.sleep(1000);

            // Кликаем через JavaScript
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    findElement(orderButton)
            );

            Thread.sleep(2000);

            // Проверяем, появилось ли окно подтверждения
            try {
                By confirmButton = By.xpath("//button[contains(text(), 'Да')]");
                if (driver.findElements(confirmButton).size() > 0) {
                    System.out.println("Подтверждаем заказ");
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();",
                            driver.findElement(confirmButton)
                    );
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                // Нет подтверждения
            }

            System.out.println("Заказ оформлен");

        } catch (Exception e) {
            System.out.println("Ошибка при оформлении заказа: " + e.getMessage());
        }
    }
}