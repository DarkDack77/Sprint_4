package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerDataForm extends BasePage {

    private By nameField = By.cssSelector("input[placeholder='* Имя']");
    private By surnameField = By.cssSelector("input[placeholder='* Фамилия']");
    private By addressField = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.cssSelector("input[placeholder='* Станция метро']");
    private By phoneField = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[contains(text(), 'Далее')]");

    public CustomerDataForm(WebDriver driver) {
        super(driver);
    }

    public void fillCustomerData(String name, String surname, String address, String metro, String phone) {
        waitForElementClickable(nameField, 10).clear();
        waitForElementClickable(nameField, 10).sendKeys(name);

        waitForElementClickable(surnameField, 10).clear();
        waitForElementClickable(surnameField, 10).sendKeys(surname);

        waitForElementClickable(addressField, 10).clear();
        waitForElementClickable(addressField, 10).sendKeys(address);

        WebElement metroInput = waitForElementClickable(metroField, 10);
        metroInput.clear();
        metroInput.sendKeys(metro);

        // Ждём появления подсказки метро и кликаем по первой
        waitForElementClickable(By.cssSelector(".select-search__option"), 5).click();

        waitForElementClickable(phoneField, 10).clear();
        waitForElementClickable(phoneField, 10).sendKeys(phone);
    }

    public void goToNextStep() {
        waitForElementClickable(nextButton, 10).click();

        // Ждём появления кнопки "Заказать" второго шага
        waitForElementPresent(By.xpath("//button[contains(text(), 'Заказать') and not(contains(@class, 'Header'))]"), 10);
    }
}