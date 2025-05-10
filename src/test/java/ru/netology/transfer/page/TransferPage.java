package ru.netology.transfer.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMassage = $("[data-test-id=error-notification] .notification__content");


    // Конструктор который проверяет наличие текста "Пополнение карты"
    public TransferPage() {
        transferHead.shouldBe(Condition.visible);
    }


    // Метод который делает валидный перевод
    public DashboardPage makeValidTransfer(String amountToTransfer, DataUser.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();

    }


    // Метод который заполняет поле сумма,откуда будет делатся перевод и кликает на кнопку пополнить
    private void makeTransfer(String amountToTransfer, DataUser.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }


    // Метод который проверяет наличе сообщение об ошибке при несостоявщемся переводе
    public void findErrrorMessage(String expectedText) {
        errorMassage.should(Condition.and("Проверка сообщения об ошибке", Condition.text(expectedText), Condition.visible));
    }
}

