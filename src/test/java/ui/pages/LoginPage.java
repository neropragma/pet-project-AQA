package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.data.User;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement usernameField = $x("//input[@id='user-name']");
    private final SelenideElement passwordField = $x("//input[@id='password']");
    private final SelenideElement loginButton = $x("//input[@id='login-button']");

    public LoginPage(){
        $x("//input[@id='login-button']").shouldBe(visible);
    }

    @Step("Открыть страницу авторизации")
    public static LoginPage openLoginPage(){
        Selenide.open("/");
        return new LoginPage();
    }

    @Step("Успешная авторизация")
    public InventoryPage successLogin(User user){
        usernameField.setValue(user.getUsername());
        passwordField.setValue(user.getPassword());
        loginButton.click();
        return new InventoryPage();
    }

    @Step("Неуспешная аворизация")
    public void unSuccessLogin(User user){
        usernameField.setValue(user.getUsername());
        passwordField.setValue(user.getPassword());
        loginButton.click();
        $x("//h3[@data-test='error']").shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }

}
