package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPopup extends BasePage {

    private By popupMessage = By.xpath("//*[contains(text(), 'Заказ оформлен') or contains(text(), 'успешно')]");

    public SuccessPopup(WebDriver driver) {
        super(driver);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            waitForElementVisible(popupMessage, 15);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}