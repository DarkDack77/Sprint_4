package ru.yandex.praktikum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для главной страницы
    private static final String ORDER_SMALL_BUTTON_XPATH = "//button[contains(@class, 'Button_Button__ra12g')]";
    private static final String ORDER_BIG_BUTTON_XPATH = "//div[contains(@class, 'Home_FinishButton')]//button[contains(text(), 'Заказать')]";
    private static final String COOKIE_BUTTON_XPATH = "//button[@class='App_CookieButton__3cvqF']";

    // Локаторы для первой формы (Для кого самокат)
    private static final String NAME_INPUT_XPATH = "//input[@placeholder='* Имя']";
    private static final String SURNAME_INPUT_XPATH = "//input[@placeholder='* Фамилия']";
    private static final String ADDRESS_INPUT_XPATH = "//input[@placeholder='* Адрес: куда привезти заказ']";
    private static final String METRO_INPUT_XPATH = "//input[@placeholder='* Станция метро']";
    private static final String PHONE_INPUT_XPATH = "//input[@placeholder='* Телефон: на него позвонит курьер']";
    private static final String NEXT_BUTTON_XPATH = "//button[contains(text(), 'Далее')]";

    // Локаторы для второй формы (Про аренду)
    private static final String DATE_INPUT_XPATH = "//input[@placeholder='* Когда привезти самокат']";
    private static final String RENTAL_DROPDOWN_XPATH = "//div[@class='Dropdown-control']";
    private static final String RENTAL_OPTION_XPATH = "//div[@class='Dropdown-option' and text()='двое суток']";
    private static final String CHOOSE_COLOR_XPATH = "//input[@id='black']";
    private static final String COMMENT_INPUT_XPATH = "//input[@placeholder='Комментарий для курьера']";
    private static final String ORDER_BUTTON_XPATH = "//button[contains(text(), 'Заказать') and contains(@class, 'Button_Middle__1CSJM')]";

    // Локаторы для подтверждения заказа
    private static final String YES_BUTTON_XPATH = "//button[contains(text(), 'Да')]";
    private static final String ORDER_CONFIRM_MODAL_XPATH = "//div[contains(@class, 'Order_ModalHeader')]";

    // Локатор для заголовка второй формы
    private static final String RENTAL_FORM_HEADER = "//div[contains(text(), 'Про аренду')]";

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void acceptCookies() {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(COOKIE_BUTTON_XPATH)));
            cookieButton.click();
            System.out.println("   ✓ Куки приняты");
        } catch (Exception e) {
            System.out.println("   Кнопка cookie не найдена");
        }
    }

    public void clickSmallOrderButton() {
        try {
            WebElement smallOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ORDER_SMALL_BUTTON_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", smallOrderButton);
            smallOrderButton.click();
            System.out.println("   ✓ Нажата верхняя кнопка 'Заказать'");
            waitForFirstFormLoaded();
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при нажатии верхней кнопки: " + e.getMessage());
        }
    }

    public void clickBigOrderButton() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement bigOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ORDER_BIG_BUTTON_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", bigOrderButton);
            bigOrderButton.click();
            System.out.println("   ✓ Нажата нижняя кнопка 'Заказать'");
            waitForFirstFormLoaded();
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при нажатии нижней кнопки: " + e.getMessage());
        }
    }

    private void waitForFirstFormLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NAME_INPUT_XPATH)));
            System.out.println("   ✓ Форма 'Для кого самокат' загружена");
        } catch (Exception e) {
            System.out.println("   ✗ Форма не загрузилась: " + e.getMessage());
        }
    }

    private void fillInputField(String xpath, String value) {
        try {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            input.clear();
            input.sendKeys(value);
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при заполнении поля " + xpath + ": " + e.getMessage());
        }
    }

    public void fillFirstForm(String name, String surname, String address, String phoneNumber) {
        fillInputField(NAME_INPUT_XPATH, name);
        fillInputField(SURNAME_INPUT_XPATH, surname);
        fillInputField(ADDRESS_INPUT_XPATH, address);
        fillInputField(PHONE_INPUT_XPATH, phoneNumber);
        fillMetroStation("Войковская");
    }

    private void fillMetroStation(String station) {
        try {
            WebElement metroInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(METRO_INPUT_XPATH)));
            metroInput.click();
            metroInput.clear();
            metroInput.sendKeys(station);
            System.out.println("   ✓ Введено название станции: " + station);

            Thread.sleep(500);

            // Используем клавиши для выбора первого варианта
            metroInput.sendKeys(Keys.DOWN);
            Thread.sleep(200);
            metroInput.sendKeys(Keys.ENTER);
            System.out.println("   ✓ Станция метро выбрана через клавиши");

        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при выборе станции метро: " + e.getMessage());
        }
    }

    public void clickNextButton() {
        try {
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NEXT_BUTTON_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
            System.out.println("   ✓ Нажата кнопка 'Далее'");
            waitForSecondFormLoaded();
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при нажатии кнопки 'Далее': " + e.getMessage());
        }
    }

    private void waitForSecondFormLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RENTAL_FORM_HEADER)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DATE_INPUT_XPATH)));
            System.out.println("   ✓ Форма 'Про аренду' загружена");
        } catch (Exception e) {
            System.out.println("   ✗ Форма 'Про аренду' не загрузилась: " + e.getMessage());
        }
    }

    public void fillSecondForm(String rentalDate, String comment) {
        try {
            WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DATE_INPUT_XPATH)));
            dateField.click();
            dateField.clear();
            dateField.sendKeys(rentalDate);
            dateField.sendKeys(Keys.ENTER);
            System.out.println("   ✓ Дата доставки: " + rentalDate);

            selectRentalPeriod();
            selectColor();

            if (comment != null && !comment.isEmpty()) {
                WebElement commentField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(COMMENT_INPUT_XPATH)));
                commentField.sendKeys(comment);
                System.out.println("   ✓ Добавлен комментарий");
            }
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при заполнении формы: " + e.getMessage());
        }
    }

    private void selectRentalPeriod() {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(RENTAL_DROPDOWN_XPATH)));
            dropdown.click();
            System.out.println("   ✓ Выпадающий список открыт");

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(RENTAL_OPTION_XPATH)));
            option.click();
            System.out.println("   ✓ Выбран срок аренды: двое суток");

        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при выборе срока: " + e.getMessage());
        }
    }

    private void selectColor() {
        try {
            WebElement blackColor = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CHOOSE_COLOR_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", blackColor);
            System.out.println("   ✓ Выбран цвет: черный");
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при выборе цвета: " + e.getMessage());
        }
    }

    public void clickOrderButton() {
        try {
            WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ORDER_BUTTON_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderButton);
            System.out.println("   ✓ Нажата кнопка 'Заказать' в форме");
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при нажатии кнопки заказа: " + e.getMessage());
        }
    }

    public void confirmOrder() {
        try {
            WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(YES_BUTTON_XPATH)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesButton);
            System.out.println("   ✓ Заказ подтвержден кнопкой 'Да'");
        } catch (Exception e) {
            System.out.println("   ✗ Ошибка при подтверждении: " + e.getMessage());
        }
    }

    public boolean isOrderConfirmed(String expectedText) {
        try {
            WebElement confirmationModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ORDER_CONFIRM_MODAL_XPATH)));
            String actualText = confirmationModal.getText();
            System.out.println("   Текст подтверждения: " + actualText);
            return actualText.contains(expectedText);
        } catch (TimeoutException e) {
            System.out.println("   ✗ Модальное окно подтверждения не появилось");
            return false;
        }
    }

    public void placeOrderViaSmallButton(String name, String surname, String address, String phone, String rentalDate, String comment) {
        System.out.println("   Начинаем оформление заказа через ВЕРХНЮЮ кнопку");
        clickSmallOrderButton();
        fillFirstForm(name, surname, address, phone);
        clickNextButton();
        fillSecondForm(rentalDate, comment);
        clickOrderButton();
        confirmOrder();
    }

    public void placeOrderViaBigButton(String name, String surname, String address, String phone, String rentalDate, String comment) {
        System.out.println("   Начинаем оформление заказа через НИЖНЮЮ кнопку");
        acceptCookies();
        clickBigOrderButton();
        fillFirstForm(name, surname, address, phone);
        clickNextButton();
        fillSecondForm(rentalDate, comment);
        clickOrderButton();
        confirmOrder();
    }

    public static Object[][] getTestParameters() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, Тверская 1", "+79991234567", "22.06.2026", "Позвонить за час"},
                {"Мария", "Петрова", "СПб, Невский 10", "+79997654321", "23.06.2026", "Домофон 123"},
                {"Алексей", "Сидоров", "Казань, Баумана 5", "+79995551234", "24.06.2026", ""}
        };
    }
}