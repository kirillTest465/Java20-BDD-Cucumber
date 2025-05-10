package ru.netology.transfer.page;

import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {


    // Метод который заполняет логин и пороль затем нажимает на кнопку продолжить
    public VerificationPage validLogin(DataUser.UserUnfo unfo) {
        $("[data-test-id=login] input").setValue(unfo.getLogin());
        $("[data-test-id=password] input").setValue(unfo.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }


}
