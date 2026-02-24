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
        findElement(nameField).clear(); findElement(nameField).sendKeys(name);
        findElement(surnameField).clear(); findElement(surnameField).sendKeys(surname);
        findElement(addressField).clear(); findElement(addressField).sendKeys(address);

        WebElement metroInput = findElement(metroField);
        metroInput.clear(); metroInput.sendKeys(metro);

        try { Thread.sleep(500); } catch (InterruptedException e) {}
        try { driver.findElement(By.cssSelector(".select-search__option")).click(); } catch (Exception e) {}

        findElement(phoneField).clear(); findElement(phoneField).sendKeys(phone);
    }

    public void goToNextStep() {
        findElement(nextButton).click();
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }
}