package ru.yandex.praktikum.tests;

import org.junit.Test;
import ru.yandex.praktikum.pages.*;

import static org.junit.Assert.assertTrue;

public class BottomOrderButtonTest extends BaseTest {

    @Test
    public void orderWithBottomButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
        mainPage.clickBottomOrderButton();

        // Заполнение формы "Для кого самокат"
        CustomerDataForm customerForm = new CustomerDataForm(driver);
        customerForm.fillCustomerData(
                "Мария",
                "Петрова",
                "СПб, Невский 10",
                "Невский проспект",
                "+79997654321"
        );
        customerForm.goToNextStep();

        // Заполнение формы "Про аренду"
        OrderDetailsForm detailsForm = new OrderDetailsForm(driver);
        detailsForm.fillAllFields("26.12.2024"); // заполняем все поля формы аренды
        detailsForm.placeOrder();

        // Проверка попапа
        SuccessPopup popup = new SuccessPopup(driver);
        assertTrue("Popup не отображается", popup.isSuccessMessageDisplayed());
    }
}