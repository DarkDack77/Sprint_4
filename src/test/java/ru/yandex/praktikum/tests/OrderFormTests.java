package ru.yandex.praktikum.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.pages.OrderFormPage;

@RunWith(Parameterized.class)
public class OrderFormTests extends BaseTest {

    private final String name;
    private final String surname;
    private final String address;
    private final String phoneNumber;
    private final String metro;
    private final String rentalDate;
    private final String comment;

    public OrderFormTests(String name, String surname, String address, String phoneNumber,
                          String metro, String rentalDate, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.metro = metro;
        this.rentalDate = rentalDate;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Заказ: {0} {1}")
    public static Object[][] getTestParameters() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, Тверская 1", "+79991234567", "Войковская", "22.06.2026", "Позвонить за час"},
                {"Мария", "Петрова", "СПб, Невский 10", "+79997654321", "Войковская", "23.06.2026", "Домофон 123"},
                {"Алексей", "Сидоров", "Казань, Баумана 5", "+79995551234", "Войковская", "24.06.2026", ""}
        };
    }

    @Test
    public void scooterOrderPositiveWithSmallButtonTest() {
        MainPage main = new MainPage(driver);
        main.open();
        main.acceptCookies();
        main.clickOrderSmall();

        OrderFormPage order = new OrderFormPage(driver);
        order.waitFirstFormLoaded();
        order.fillFirstForm(name, surname, address, phoneNumber, metro);
        order.clickNext();
        order.fillSecondForm(rentalDate, comment);
        order.clickOrder();
        order.confirmOrder();

        Assert.assertTrue("Заказ не был подтвержден", order.isOrderConfirmedContains("Заказ оформлен"));
    }

    @Test
    public void scooterOrderPositiveWithBigButtonTest() {
        MainPage main = new MainPage(driver);
        main.open();
        main.acceptCookies();
        main.clickOrderBig();

        OrderFormPage order = new OrderFormPage(driver);
        order.waitFirstFormLoaded();
        order.fillFirstForm(name, surname, address, phoneNumber, metro);
        order.clickNext();
        order.fillSecondForm(rentalDate, comment);
        order.clickOrder();
        order.confirmOrder();

        Assert.assertTrue("Заказ не был подтвержден", order.isOrderConfirmedContains("Заказ оформлен"));
    }
}