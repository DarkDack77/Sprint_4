package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderFormPage extends BasePage {

    // Локаторы для первой формы (Для кого самокат)
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[normalize-space()='Далее']");

    // Выпадающий список метро (первый вариант)
    private final By metroFirstOption = By.xpath("//div[contains(@class,'select-search__select')]//li[1]");

    // Локаторы для второй формы (Про аренду)
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown = By.xpath("//div[@class='Dropdown-control']");
    private final By rentalOptionTwoDays = By.xpath("//div[@class='Dropdown-option' and normalize-space()='двое суток']");
    private final By colorBlack = By.id("black");
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class,'Button_Middle__1CSJM') and normalize-space()='Заказать']");

    // Подтверждение
    private final By yesButton = By.xpath("//button[normalize-space()='Да']");
    private final By orderConfirmModalHeader = By.xpath("//div[contains(@class,'Order_ModalHeader')]");

    // Заголовок второй формы
    private final By rentalFormHeader = By.xpath("//div[contains(text(),'Про аренду')]");

    public OrderFormPage(WebDriver driver) {
        super(driver);
    }

    // ожидание первой формы
    public void waitFirstFormLoaded() {
        waitVisible(nameInput);
    }

    // ожидание второй формы
    public void waitSecondFormLoaded() {
        waitVisible(rentalFormHeader);
        waitClickable(dateInput);
    }

    public void fillFirstForm(String name, String surname, String address, String phoneNumber, String metroStation) {
        type(nameInput, name);
        type(surnameInput, surname);
        type(addressInput, address);
        type(phoneInput, phoneNumber);
        selectMetroStation(metroStation);
    }

    private void selectMetroStation(String station) {
        // вводим текст
        type(metroInput, station);
        // ждём что появится список и кликаем первый вариант
        click(metroFirstOption);
    }

    public void clickNext() {
        clickWithJs(nextButton);
        waitSecondFormLoaded();
    }

    public void fillSecondForm(String rentalDate, String comment) {
        type(dateInput, rentalDate);
        // закрываем календарь
        waitClickable(dateInput).sendKeys(Keys.ENTER);

        click(rentalDropdown);
        click(rentalOptionTwoDays);

        clickWithJs(colorBlack);

        if (comment != null && !comment.isEmpty()) {
            type(commentInput, comment);
        }
    }

    public void clickOrder() {
        clickWithJs(orderButton);
    }

    public void confirmOrder() {
        clickWithJs(yesButton);
    }

    public boolean isOrderConfirmedContains(String expectedText) {
        String text = waitVisible(orderConfirmModalHeader).getText();
        return text.contains(expectedText);
    }
}