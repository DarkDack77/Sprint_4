package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderDetailsForm extends BasePage {

    private By commentField = By.xpath("//textarea[contains(@placeholder, 'Комментарий')]");
    private By orderButton = By.xpath("//button[contains(text(), 'Заказать') and not(contains(@class, 'Header'))]");

    public OrderDetailsForm(WebDriver driver) {
        super(driver);
    }

    public void addComment(String comment) {
        try {
            waitForElementClickable(commentField, 10).sendKeys(comment);
        } catch (Exception e) {
            // Если поле комментария не найдено — пропускаем (не критично)
        }
    }

    public void placeOrder() {
        waitForElementClickable(orderButton, 10).click();
    }
}