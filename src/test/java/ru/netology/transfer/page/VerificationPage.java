package ru.netology.transfer.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeFied = $("[data-test-id=code] input");
    private final SelenideElement verifeButton = $("[data-test-id=action-verify]");

    // Конструктор который проверяет видимость поля ввода кода верификации
    public VerificationPage() {
        codeFied.shouldBe(Condition.visible);
    }

    // метод который заполняет код верификации и жмет на кнопку продолжить
    public DashboardPage validVerify(String verificationCode) {
        codeFied.setValue(DataUser.getUserVerification().getVerificationCode());
        verifeButton.click();
        return new DashboardPage();
    }
}
