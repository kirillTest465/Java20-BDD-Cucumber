package ru.netology.transfer.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.transfer.data.DataUser;
import ru.netology.transfer.page.DashboardPage;
import ru.netology.transfer.page.LoginPage;
import ru.netology.transfer.page.VerificationPage;
import ru.netology.transfer.page.TransferPage;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static DataUser dataUser;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void verifcation(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(new DataUser.UserUnfo(login, password));
        dashboardPage = verificationPage.validVerify(DataUser.getUserVerification().getVerificationCode());

    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void transferCardToCard(String transferAmount, String cardNumber, String selectedCard) {
        dashboardPage.selectCardToTransfer(new DataUser.CardInfo(cardNumber, selectedCard));
        var transfer = new TransferPage().makeValidTransfer(transferAmount, new DataUser.CardInfo(cardNumber, selectedCard));

    }


    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void checkingBalance(String cardNumber, String cardBalance) {
        var balance = dashboardPage.getCardBalance(cardNumber);

        String expected = cardBalance;
        String actual = String.valueOf(balance);
        Assertions.assertEquals(expected, actual);

    }
}
