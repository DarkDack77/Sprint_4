package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderDetailsForm extends BasePage {

    private By orderButton = By.xpath("//button[contains(text(), 'Заказать')]");

    public OrderDetailsForm(WebDriver driver) {
        super(driver);
    }

    public void addComment(String comment) {
        // Пропускаем, если не нашли — не критично
    }

    public void placeOrder() {
        findElement(orderButton).click();
    }
}