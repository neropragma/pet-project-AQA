package ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.BaseTest;

import static ui.data.Users.locked_out_user;
import static ui.data.Users.standard_user;
import static ui.pages.LoginPage.openLoginPage;

@Tag("UI")
public class LoginTests extends BaseTest {

    @Test
    @Owner("m.radkevich")
    @DisplayName("Успешная авторизация")
    @Description("Авторизации для стандартного пользователя и деавторизация")
    public void successLoginTest(){
        openLoginPage()
                .successLogin(standard_user)
                .logout();
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Неуспешная авторизация")
    @Description("Попытка авторизации для заблокированного пользователя")
    public void unSuccessLoginTest(){
        openLoginPage()
                .unSuccessLogin(locked_out_user);
    }
}
