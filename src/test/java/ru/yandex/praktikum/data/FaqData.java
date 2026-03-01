package ru.yandex.praktikum.data;

public class FaqData {
    private final int index;
    private final String expectedAnswer;

    public FaqData(int index, String expectedAnswer) {
        this.index = index;
        this.expectedAnswer = expectedAnswer;
    }

    public int getIndex() {
        return index;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }
}