package ru.netology.transfer.data;

import lombok.Value;

public class DataUser {


    // Метод для который генерирует юзера
    public static UserUnfo getUserUnfo() {
        String login = "vasya";
        String password = "qwerty123";

        return new UserUnfo(login,password);
    }

public static UserVerification getUserVerification() {
    String verification = "12345";
    return new UserVerification(verification);
}


    // Метод для хранения данных первой карты
    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }


    // Метод для хранения данных второй карты
    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    // Метод который возвращает валидную сумму возвращая абсолютное значение и делит его на 10
    public static int generateValidAmount(int balance) {
        return Math.abs(balance) / 10;
    }


    // Метод который возвращает недопустимую сумму возвращая абсолютное значение и прибавляя к нему 1
    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + 1;
    }

    // Статический класс который хранит в себе логин, пароль, код верификации
    @Value
    public static class UserUnfo {
        String login;
        String password;
    }

    @Value
    public static class UserVerification {
        String verificationCode;
    }


    // Статический класс который хранит в себе номер карты и id карты
    @Value
    public static class CardInfo {
        String CardNumber;
        String testId;
    }
}

