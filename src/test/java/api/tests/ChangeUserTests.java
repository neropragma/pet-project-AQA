package api.tests;

import api.models.user.change.CreateResponse;
import api.models.user.change.UpdateResponse;
import api.specs.Specifications;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.time.Clock;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Tag("API")
public class ChangeUserTests {

    @RetryingTest(2)
    @Owner("m.radkevich")
    @DisplayName("Создание пользователя")
    @Description("Выполняется запрос на создание пользователя " +
            "и проверяется корррктность внесенных на сервер данных")
    public void createUser(){
        Map<String, String> user = Map.of(
                "name", "morpheus",
                "job", "leader"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecWithParam(201));
        CreateResponse createResp = given()
                .body(user)
                .when()
                .post("/api/users")
                .then().log().all()
                .extract().as(CreateResponse.class);

        String regex = ".\\..*$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");

        Assertions.assertEquals(user.get("name"), createResp.getName());
        Assertions.assertEquals(user.get("job"), createResp.getJob());
        Assertions.assertTrue(Integer.parseInt(createResp.getId())>0);
        Assertions.assertEquals(currentTime, createResp.getCreatedAt().replaceAll(regex, ""));
    }

    @RetryingTest(2)
    @Owner("m.radkevich")
    @DisplayName("Полное обновление пользователя")
    @Description("Выполняется запрос на полное обновление пользователя " +
            "и проверяется корррктность внесенных на сервер данных")
    public void updateByPutUser(){
        Map<String, String> user = Map.of(
                "name", "morpheus",
                "job", "zion resident"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        UpdateResponse createResp = given()
                .body(user)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UpdateResponse.class);

        String regex = ".\\..*$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");

        Assertions.assertEquals(user.get("name"), createResp.getName());
        Assertions.assertEquals(user.get("job"), createResp.getJob());
        Assertions.assertEquals(currentTime, createResp.getUpdatedAt().replaceAll(regex, ""));
    }

    @RetryingTest(2)
    @Owner("m.radkevich")
    @DisplayName("Частичное обновление пользователя")
    @Description("Выполняется запрос на частичное обновление пользователя " +
            "и проверяется корррктность внесенных на сервер данных")
    public void updateByPatchUser(){
        Map<String, String> user = Map.of(
                "name", "morpheus",
                "job", "zion resident"
        );

        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        UpdateResponse createResp = given()
                .body(user)
                .when()
                .patch("/api/users/2")
                .then().log().all()
                .extract().as(UpdateResponse.class);

        String regex = ".\\..*$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");

        Assertions.assertEquals(user.get("name"), createResp.getName());
        Assertions.assertEquals(user.get("job"), createResp.getJob());
        Assertions.assertEquals(currentTime, createResp.getUpdatedAt().replaceAll(regex, ""));
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Удаление пользователя")
    @Description("Выполняется запрос на удаление пользователя")
    public void deleteUser(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecWithParam(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }
}
