package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    // cookies (по твоим замечаниям у тебя было и By.id и xpath — оставим более стабильный id)
    private final By cookieButton = By.id("rcc-confirm-button");

    // Кнопки "Заказать" на главной
    // Верхняя: первая подходящая кнопка на странице
    private final By orderSmallButton = By.xpath("(//button[contains(@class,'Button_Button__ra12g') and normalize-space()='Заказать'])[1]");
    // Нижняя: "Заказать" в блоке Home_FinishButton
    private final By orderBigButton = By.xpath("//div[contains(@class,'Home_FinishButton')]//button[normalize-space()='Заказать']");

    // FAQ: клик по вопросу по тексту
    private By faqQuestionByText(String questionText) {
        return By.xpath("//div[contains(@class,'accordion__heading')][normalize-space()='" + questionText + "']");
    }

    // Ответ — берем панель, которая идет сразу после heading
    private By faqAnswerForQuestionText(String questionText) {
        return By.xpath("//div[contains(@class,'accordion__heading')][normalize-space()='" + questionText + "']/following-sibling::div[contains(@class,'accordion__panel')]");
    }

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
    }

    public void acceptCookies() {
        click(cookieButton);
    }

    public void clickOrderSmall() {
        scrollTo(orderSmallButton);
        click(orderSmallButton);
    }

    public void clickOrderBig() {
        scrollTo(orderBigButton);
        click(orderBigButton);
    }

    public void clickFaqQuestion(String questionText) {
        By q = faqQuestionByText(questionText);
        scrollTo(q);
        click(q);
    }

    public String getFaqAnswerText(String questionText) {
        return waitVisible(faqAnswerForQuestionText(questionText)).getText();
    }
}