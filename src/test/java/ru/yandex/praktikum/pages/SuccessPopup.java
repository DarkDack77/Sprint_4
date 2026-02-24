package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPopup extends BasePage {

    private By popupMessage = By.xpath("//*[contains(text(), 'успешно') or contains(text(), 'Заказ') or contains(text(), 'success')]");

    public SuccessPopup(WebDriver driver) {
        super(driver);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(popupMessage));
            return true;
        } catch (Exception e) {
            System.out.println("Popup успеха не найден: " + e.getMessage());
            return false;
        }
    }
}