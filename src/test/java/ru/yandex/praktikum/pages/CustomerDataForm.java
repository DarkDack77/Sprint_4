package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CustomerDataForm extends BasePage {

    private By nameField = By.cssSelector("input[placeholder='* Имя']");
    private By surnameField = By.cssSelector("input[placeholder='* Фамилия']");
    private By addressField = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.cssSelector("input[placeholder='* Станция метро']");
    private By phoneField = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[contains(text(), 'Далее')]");
    private By orderHeader = By.xpath("//div[contains(text(), 'Про аренду')]");

    public CustomerDataForm(WebDriver driver) {
        super(driver);
    }

    public void fillCustomerData(String name, String surname, String address, String metro, String phone) {
        System.out.println("   Заполняем форму 'Для кого самокат'...");

        WebElement nameInput = findElement(nameField);
        nameInput.clear();
        nameInput.sendKeys(name);

        WebElement surnameInput = findElement(surnameField);
        surnameInput.clear();
        surnameInput.sendKeys(surname);

        WebElement addressInput = findElement(addressField);
        addressInput.clear();
        addressInput.sendKeys(address);

        selectMetroStation(metro);

        WebElement phoneInput = findElement(phoneField);
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        System.out.println("   Данные заполнены");
    }

    private void selectMetroStation(String stationName) {
        WebElement metroInput = findElement(metroField);
        metroInput.click();
        metroInput.clear();
        metroInput.sendKeys(stationName);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        try {
            By optionLocator = By.className("select-search__option");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            System.out.println("   Станция выбрана через select-search__option");
        } catch (Exception e) {
            try {
                metroInput.sendKeys(Keys.ENTER);
                System.out.println("   Станция выбрана через Enter");
            } catch (Exception ex) {
                System.out.println("   Не удалось выбрать станцию метро");
            }
        }

        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void goToNextStep() {
        System.out.println("   Нажимаем кнопку 'Далее'...");

        WebElement nextBtn = waitForElementClickable(nextButton, 5);

        // Пробуем обычный клик
        try {
            nextBtn.click();
            System.out.println("   ✓ Обычный клик сработал");
        } catch (Exception e) {
            System.out.println("   ✗ Обычный клик не сработал: " + e.getMessage());

            // Пробуем JavaScript клик
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
                System.out.println("   ✓ JavaScript клик сработал");
            } catch (Exception ex) {
                System.out.println("   ✗ JavaScript клик тоже не сработал: " + ex.getMessage());
            }
        }

        System.out.println("   Ожидаем загрузки формы 'Про аренду'...");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderHeader));
            System.out.println("   ✓ Форма 'Про аренду' загружена");
        } catch (Exception e) {
            System.out.println("   ✗ Форма 'Про аренду' не загрузилась!");
        }

        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
}