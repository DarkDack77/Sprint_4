package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPopup extends BasePage {

    // Пробуем разные локаторы для попапа
    private By popupByClass = By.className("Order_Modal__YZgUN");
    private By popupByText = By.xpath("//div[contains(text(), 'Заказ оформлен')]");
    private By popupByButton = By.xpath("//button[contains(text(), 'Посмотреть статус')]");
    private By popupAny = By.xpath("//div[contains(@class, 'Modal') or contains(@class, 'Order')]");

    public SuccessPopup(WebDriver driver) {
        super(driver);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            System.out.println("Ожидаем появление попапа...");

            // Ждем 15 секунд появления любого из возможных локаторов
            boolean found = false;
            long startTime = System.currentTimeMillis();
            long timeout = 15000; // 15 секунд

            while (!found && (System.currentTimeMillis() - startTime) < timeout) {
                try {
                    if (driver.findElements(popupByClass).size() > 0 ||
                            driver.findElements(popupByText).size() > 0 ||
                            driver.findElements(popupByButton).size() > 0 ||
                            driver.findElements(popupAny).size() > 0) {
                        found = true;
                        System.out.println("✓ Попап обнаружен!");
                        break;
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    // Игнорируем
                }
            }

            if (found) {
                return true;
            } else {
                System.out.println("✗ Попап не появился за 15 секунд");
                return false;
            }

        } catch (Exception e) {
            System.out.println("✗ Ошибка при проверке попапа: " + e.getMessage());
            return false;
        }
    }
}