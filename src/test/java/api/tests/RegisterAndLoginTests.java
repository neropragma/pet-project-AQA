package api.tests;

import api.models.register.SuccessResponse;
import api.specs.Specifications;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Tag("API")
public class RegisterAndLoginTests {

    @Test
    @Owner("m.radkevich")
    @DisplayName("Успешная регистрация")
    @Description("Выполняется запрос на регистрацию пользователя " +
            "и проверяются ответные данные об id и токене пользователя")
    public void successRegisterTest(){
        Map<String, String> registerData = Map.of(
                "email", "eve.holt@reqres.in",
                "password", "pistol"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        SuccessResponse successResp = given()
                .body(registerData)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessResponse.class);

        Assertions.assertEquals(4, successResp.getId());
        Assertions.assertEquals("QpwL5tke4Pnpja7X4", successResp.getToken());
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Неуспешная регистрация")
    @Description("Выполняется запрос на регистрацию пользователя и проверяются ответные данные ошибке")
    public void unSuccessRegisterTest(){
        Map<String, String> registerData = Map.of(
                "email", "sydney@fife"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecWithParam(400));
        String error = given()
                .body(registerData)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().body().jsonPath().getString("error");

        Assertions.assertEquals("Missing password", error);
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Успешная авторизация")
    @Description("Выполняется запрос на авторизацию пользователя " +
            "и проверяются ответные данные о токене пользователя")
    public void successLoginTest(){
        Map<String, String> registerData = Map.of(
                "email", "eve.holt@reqres.in",
                "password", "cityslicka"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        String token = given()
                .body(registerData)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().body().jsonPath().getString("token");

        Assertions.assertEquals("QpwL5tke4Pnpja7X4", token);
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Неуспешная авторизация")
    @Description("Выполняется запрос на авторизацию пользователя и проверяются ответные данные ошибке")
    public void unSuccessLoginTest(){
        Map<String, String> registerData = Map.of(
                "email", "peter@klaven"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecWithParam(400));
        String error = given()
                .body(registerData)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().body().jsonPath().getString("error");

        Assertions.assertEquals("Missing password", error);
    }
}
