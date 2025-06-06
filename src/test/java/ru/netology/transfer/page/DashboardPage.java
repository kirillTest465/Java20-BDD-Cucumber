package ru.netology.transfer.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id=action-reload]");


    // Конструктор при создание которого проверяется наличие видимости "Личный кабинет"
    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }


    // Метод для получения баланса карты
    public int getCardBalance(String cardNumber) {
        // Определяем индекс карты:
        // - Если номер заканчивается на "0001" → индекс 1 (теперь вторая карта)
        // - Если номер заканчивается на "0002" → индекс 0 (теперь первая карта)
        int cardIndex = cardNumber.endsWith("0001") ? 1 : 0;

        // Получаем карту из коллекции по индексу
        var cardElement = cards.get(cardIndex);

        // Извлекаем баланс
        return extractBalance(cardElement.getText());
    }


    // Метод кликает на кнопку пополнить напротив карты с номером "5559 0000 0000 0001"
    public TransferPage selectCardToTransfer(DataUser.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    // Метод для поиска нужной карты по ID
    private SelenideElement getCard(DataUser.CardInfo cardInfo) {
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    // Метод который нажимает на кнопку обновить и проверяет после этого наличие видимости "Личный кабинет"
    public void reloadDashboardPage() {
        reloadButton.click();
        heading.shouldBe(Condition.visible);
    }


    // Метод который извлекает подстроку баланса из строки
    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
