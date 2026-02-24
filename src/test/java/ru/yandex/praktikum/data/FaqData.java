package ru.yandex.praktikum.data;

public class FaqData {
    private final String question;
    private final String expectedAnswer;

    public FaqData(String question, String expectedAnswer) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }
}