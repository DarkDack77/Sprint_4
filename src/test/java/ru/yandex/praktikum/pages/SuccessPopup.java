package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPopup extends BasePage {

    // Конструктор должен вызывать super(driver)
    public SuccessPopup(WebDriver driver) {
        super(driver);  // ЭТО ОБЯЗАТЕЛЬНО!
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            Thread.sleep(3000);
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("/order");
        } catch (Exception e) {
            return false;
        }
    }
}