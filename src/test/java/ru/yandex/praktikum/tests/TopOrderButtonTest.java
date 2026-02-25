package ru.yandex.praktikum.tests;

import org.junit.Test;
import ru.yandex.praktikum.pages.*;

import static org.junit.Assert.assertTrue;

public class TopOrderButtonTest extends BaseTest {

    @Test
    public void orderWithTopButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
        mainPage.clickTopOrderButton();

        // Заполнение формы "Для кого самокат"
        CustomerDataForm customerForm = new CustomerDataForm(driver);
        customerForm.fillCustomerData(
                "Иван",
                "Иванов",
                "Москва, Тверская 1",
                "Тверская",
                "+79991234567"
        );
        customerForm.goToNextStep();

        // Заполнение формы "Про аренду"
        OrderDetailsForm detailsForm = new OrderDetailsForm(driver);
        detailsForm.fillAllFields("25.12.2024"); // заполняем все поля формы аренды
        detailsForm.placeOrder();

        // Проверка попапа
        SuccessPopup popup = new SuccessPopup(driver);
        assertTrue("Popup не отображается", popup.isSuccessMessageDisplayed());
    }
}