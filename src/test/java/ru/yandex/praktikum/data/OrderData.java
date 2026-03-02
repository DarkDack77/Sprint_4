package ru.yandex.praktikum.data;

public class OrderData {
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String deliveryDate;
    private final String comment;

    public OrderData(String name, String surname, String address, String metro, String phone,
                     String deliveryDate, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getMetro() { return metro; }
    public String getPhone() { return phone; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getComment() { return comment; }
}