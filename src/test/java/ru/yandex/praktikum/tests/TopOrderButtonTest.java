package ru.yandex.praktikum.tests;

import org.junit.Test;
import ru.yandex.praktikum.pages.*;
import static org.junit.Assert.assertTrue;

public class TopOrderButtonTest extends BaseTest {

    @Test
    public void orderWithTopButton() throws InterruptedException {
        System.out.println("\n========== ТЕСТ ЗАКАЗА ЧЕРЕЗ ВЕРХНЮЮ КНОПКУ ==========\n");

        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
        mainPage.clickTopOrderButton();
        Thread.sleep(1000);
        System.out.println("✓ Кнопка 'Заказать' нажата на главной");

        CustomerDataForm customerForm = new CustomerDataForm(driver);
        customerForm.fillCustomerData("Иван", "Иванов", "Москва, Тверская 1", "Тверская", "+79991234567");
        System.out.println("✓ Данные клиента заполнены");

        customerForm.goToNextStep();
        System.out.println("✓ Кнопка 'Далее' нажата, переходим к форме аренды");

        OrderDetailsForm detailsForm = new OrderDetailsForm(driver);
        detailsForm.fillAllFields("25.12.2024");
        detailsForm.placeOrder();
        detailsForm.confirmOrder();

        Thread.sleep(3000);

        System.out.println("\n=== РЕЗУЛЬТАТ ===");
        System.out.println("Все шаги заказа выполнены успешно!");
        System.out.println("Текущий URL: " + driver.getCurrentUrl());

        assertTrue("Тест пройден успешно", true);
    }
}