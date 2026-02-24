package ru.yandex.praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.praktikum.pages.*;

import static org.junit.Assert.assertTrue;

public class BottomOrderButtonTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        // options.addArguments("-headless"); // ЗАКОММЕНТИРОВАНО для отладки
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderWithBottomButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.dismissCookieBanner();
        mainPage.clickBottomOrderButton();

        CustomerDataForm customerForm = new CustomerDataForm(driver);
        customerForm.fillCustomerData(
                "Мария", "Петрова", "СПб, Невский 10", "Невский проспект", "+79997654321"
        );
        customerForm.goToNextStep();

        OrderDetailsForm detailsForm = new OrderDetailsForm(driver);
        detailsForm.placeOrder();

        SuccessPopup popup = new SuccessPopup(driver);
        assertTrue("Popup не отображается", popup.isSuccessMessageDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}