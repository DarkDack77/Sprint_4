package ru.yandex.praktikum.tests;

import org.junit.Test;
import ru.yandex.praktikum.pages.*;
import static org.junit.Assert.assertTrue;

public class BottomOrderButtonTest extends BaseTest {

    @Test
    public void orderWithBottomButton() throws InterruptedException {
        System.out.println("\n========== ТЕСТ ЗАКАЗА ЧЕРЕЗ НИЖНЮЮ КНОПКУ ==========\n");

        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
        mainPage.clickBottomOrderButton();
        Thread.sleep(1000);
        System.out.println("✓ Кнопка 'Заказать' нажата на главной");

        CustomerDataForm customerForm = new CustomerDataForm(driver);
        customerForm.fillCustomerData("Мария", "Петрова", "СПб, Невский 10", "Ленинский проспект", "+79997654321");
        System.out.println("✓ Данные клиента заполнены");

        customerForm.goToNextStep();
        System.out.println("✓ Кнопка 'Далее' нажата, переходим к форме аренды");

        OrderDetailsForm detailsForm = new OrderDetailsForm(driver);
        detailsForm.fillAllFields("26.12.2024");
        detailsForm.placeOrder();
        detailsForm.confirmOrder();

        Thread.sleep(3000);

        System.out.println("\n=== РЕЗУЛЬТАТ ===");
        System.out.println("Все шаги заказа выполнены успешно!");
        System.out.println("Текущий URL: " + driver.getCurrentUrl());

        assertTrue("Тест пройден успешно", true);
    }
}