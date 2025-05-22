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
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginUser(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(new DataUser.UserUnfo(login, password));
        dashboardPage = verificationPage.validVerify(DataUser.getUserVerification().getVerificationCode());
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void transferMoney(String transferAmount, String fromCardNumber, String toCardTestId) {
        // Получаем информацию о карте-получателе по testId
        DataUser.CardInfo toCard = getCardByTestId(toCardTestId);

        // Выбираем карту для пополнения
        transferPage = dashboardPage.selectCardToTransfer(toCard);

        // Получаем информацию о карте-отправителе по номеру
        DataUser.CardInfo fromCard = getCardByNumber(fromCardNumber);

        // Выполняем перевод (убираем пробелы в сумме)
        dashboardPage = transferPage.makeValidTransfer(transferAmount.replace(" ", ""), fromCard);
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void verifyCardBalance(String cardTestId, String expectedBalance) {
        // Получаем информацию о карте по testId
        DataUser.CardInfo card = getCardByTestId(cardTestId);

        // Получаем текущий баланс
        int actualBalance = dashboardPage.getcardBalance(card);

        // Проверяем соответствие баланса (убираем пробелы в expectedBalance)
        Assertions.assertEquals(
                Integer.parseInt(expectedBalance.replace(" ", "")),
                actualBalance,
                String.format("Ожидаемый баланс: %s, Фактический баланс: %d", expectedBalance, actualBalance)
        );
    }

    // Вспомогательный метод для получения карты по номеру
    private DataUser.CardInfo getCardByNumber(String cardNumber) {
        if (cardNumber.equals(DataUser.getFirstCardInfo().getCardNumber())) {
            return DataUser.getFirstCardInfo();
        } else if (cardNumber.equals(DataUser.getSecondCardInfo().getCardNumber())) {
            return DataUser.getSecondCardInfo();
        }
        throw new IllegalArgumentException("Карта с номером " + cardNumber + " не найдена");
    }

    // Вспомогательный метод для получения карты по testId
    private DataUser.CardInfo getCardByTestId(String testId) {
        if (testId.equals(DataUser.getFirstCardInfo().getTestId())) {
            return DataUser.getFirstCardInfo();
        } else if (testId.equals(DataUser.getSecondCardInfo().getTestId())) {
            return DataUser.getSecondCardInfo();
        }
        throw new IllegalArgumentException("Карта с testId " + testId + " не найдена");
    }
}