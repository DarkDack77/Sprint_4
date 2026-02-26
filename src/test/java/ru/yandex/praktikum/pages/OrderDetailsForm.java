package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderDetailsForm extends BasePage {

    private By deliveryDateField = By.cssSelector("input[placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-control");
    private By rentalPeriodPlaceholder = By.className("Dropdown-placeholder");
    private By colorBlackCheckbox = By.id("black");

    private By orderButton1 = By.xpath("//button[contains(text(), 'Заказать') and contains(@class, 'Button')]");
    private By orderButton2 = By.xpath("//div[@class='Order_Content__bmtHS']//button[contains(text(), 'Заказать')]");
    private By orderButton3 = By.xpath("//button[contains(text(), 'Заказать')]");

    public OrderDetailsForm(WebDriver driver) {
        super(driver);
    }

    public void fillAllFields(String deliveryDate) {
        System.out.println("1. Ожидаем загрузки формы 'Про аренду'...");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));
            System.out.println("   ✓ Форма 'Про аренду' загружена");
        } catch (Exception e) {
            System.out.println("   ✗ Форма 'Про аренду' не загрузилась!");
            return;
        }

        System.out.println("2. Заполняем дату: " + deliveryDate);
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.click();
        dateField.clear();
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.ENTER);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        System.out.println("3. Выбираем срок аренды");
        selectRentalPeriod();

        System.out.println("4. Выбираем цвет самоката");
        selectColor();

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        System.out.println("5. Все поля заполнены");
    }

    public void selectRentalPeriod() {
        System.out.println("   Начинаем выбор срока аренды...");

        try {
            // Способ 1: Клик по полю и выпадающий список
            System.out.println("   Способ 1: Клик по полю и выбор из списка");

            // Ждем, когда поле станет кликабельным
            WebElement periodField = waitForElementClickable(rentalPeriodField, 10);

            // Кликаем через JavaScript для надежности
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", periodField);
            System.out.println("   ✓ Поле открыто");

            try { Thread.sleep(1000); } catch (InterruptedException e) {}

            // Ждем появления выпадающего списка
            By dropdownMenu = By.className("Dropdown-menu");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownMenu));
            System.out.println("   ✓ Выпадающий список появился");

            // Выбираем первый вариант
            By firstOption = By.xpath("//div[@class='Dropdown-option' and text()='сутки']");
            WebElement option = driver.findElement(firstOption);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);

            System.out.println("   ✓ Срок аренды выбран (способ 1)");
            return;

        } catch (Exception e) {
            System.out.println("   ✗ Способ 1 не сработал: " + e.getMessage());

            try {
                // Способ 2: Используем стрелки
                System.out.println("   Способ 2: Используем стрелки вниз + Enter");

                WebElement periodField = driver.findElement(rentalPeriodField);
                periodField.click();
                try { Thread.sleep(500); } catch (InterruptedException ex) {}

                periodField.sendKeys(Keys.ARROW_DOWN);
                try { Thread.sleep(500); } catch (InterruptedException ex) {}
                periodField.sendKeys(Keys.ENTER);

                System.out.println("   ✓ Срок аренды выбран (способ 2)");
                return;

            } catch (Exception ex) {
                System.out.println("   ✗ Способ 2 не сработал: " + ex.getMessage());

                try {
                    // Способ 3: JavaScript
                    System.out.println("   Способ 3: JavaScript");

                    String script =
                            "var field = document.querySelector('.Dropdown-control');" +
                                    "if(field) {" +
                                    "  field.click();" +
                                    "  setTimeout(function() {" +
                                    "    var options = document.querySelectorAll('.Dropdown-option');" +
                                    "    if(options.length > 0) options[0].click();" +
                                    "  }, 500);" +
                                    "}";

                    ((JavascriptExecutor) driver).executeScript(script);
                    try { Thread.sleep(2000); } catch (InterruptedException ie) {}

                    System.out.println("   ✓ Срок аренды выбран (способ 3)");

                } catch (Exception exc) {
                    System.out.println("   ✗ Все способы выбора срока аренды не сработали");
                }
            }
        }
    }

    public void selectColor() {
        try {
            WebElement colorCheckbox = waitForElementClickable(colorBlackCheckbox, 5);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", colorCheckbox);
            System.out.println("   ✓ Цвет выбран");
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при выборе цвета: " + e.getMessage());
        }
    }

    public void placeOrder() {
        System.out.println("6. Ищем кнопку 'Заказать'...");

        WebElement orderBtn = null;
        By[] orderButtons = {orderButton1, orderButton2, orderButton3};

        for (By buttonLocator : orderButtons) {
            try {
                if (driver.findElements(buttonLocator).size() > 0) {
                    orderBtn = driver.findElement(buttonLocator);
                    System.out.println("   ✓ Кнопка найдена по локатору: " + buttonLocator);
                    break;
                }
            } catch (Exception e) {}
        }

        if (orderBtn == null) {
            System.out.println("   ✗ Кнопка 'Заказать' не найдена!");
            return;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", orderBtn);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        System.out.println("7. Нажимаем кнопку 'Заказать'...");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderBtn);

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        System.out.println("   ✓ Кнопка 'Заказать' нажата");
    }

    public void confirmOrder() {
        System.out.println("8. Ищем кнопку подтверждения...");

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        try {
            By yesButton = By.xpath("//div[contains(@class, 'Order_Modal')]//button[contains(text(), 'Да')]");
            WebElement yesBtn = waitForElementClickable(yesButton, 10);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
            System.out.println("9. Заказ подтвержден!");
        } catch (Exception e) {
            System.out.println("   ✗ Кнопка 'Да' не найдена");
        }

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }
}