package ru.yandex.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Assert;
import ru.yandex.praktikum.pages.OrderFormPage;

@RunWith(Parameterized.class)
public class OrderFormTests extends BaseTest {

    private final String name;
    private final String surname;
    private final String address;
    private final String phoneNumber;
    private final String rentalDate;
    private final String comment;

    // ЭТО КОНСТРУКТОР - у него нет возвращаемого типа!
    public OrderFormTests(String name, String surname, String address, String phoneNumber,
                          String rentalDate, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rentalDate = rentalDate;
        this.comment = comment;
    }

    // ЭТО МЕТОД - у него есть возвращаемый тип Object[][]
    @Parameterized.Parameters(name = "Тест: {0} {1}")
    public static Object[][] getTestParameters() {
        return OrderFormPage.getTestParameters();
    }

    @Test
    public void scooterOrderPositiveWithSmallButtonTest() {
        driver.get(OrderFormPage.BASE_URL);
        OrderFormPage orderForm = new OrderFormPage(driver);

        orderForm.placeOrderViaSmallButton(name, surname, address, phoneNumber, rentalDate, comment);

        Assert.assertTrue("Заказ не был подтвержден", orderForm.isOrderConfirmed("Заказ оформлен"));
    }

    @Test
    public void scooterOrderPositiveWithBigButtonTest() {
        driver.get(OrderFormPage.BASE_URL);
        OrderFormPage orderForm = new OrderFormPage(driver);

        orderForm.placeOrderViaBigButton(name, surname, address, phoneNumber, rentalDate, comment);

        Assert.assertTrue("Заказ не был подтвержден", orderForm.isOrderConfirmed("Заказ оформлен"));
    }
}