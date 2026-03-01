package ru.yandex.praktikum.tests;

import org.junit.Test;
import ru.yandex.praktikum.BaseTest;
import ru.yandex.praktikum.pages.OrderFormPage;

public class OrderFormTests extends BaseTest {

    @Test
    public void testOrderForm() {
        OrderFormPage orderFormPage = new OrderFormPage(driver);
        // Здесь твои тесты для формы заказа
        // orderFormPage.fillOrderForm();
        // orderFormPage.submitOrder();
    }

    // Добавь другие тесты
}