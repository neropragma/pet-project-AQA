package api.tests;

import api.models.user.get.GetUser;
import api.models.user.get.GetUserList;
import api.specs.Specifications;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Tag("API")
public class GetUserTests {

    @Test
    @Owner("m.radkevich")
    @DisplayName("Получение списка пользователей")
    @Description("Ввыполнятся запрос на получение списка пользователей со 2 страницы " +
            "и проверяется, что заявленное количество пользователей соответсвует полученному")
    public void checkObtainListOfUsers(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        GetUserList getUserList = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .extract().as(GetUserList.class);

        Assertions.assertEquals(12, getUserList.getTotal());
        Assertions.assertEquals(2, getUserList.getPage());
        Assertions.assertEquals(getUserList.getPer_page(), getUserList.getData().size());
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Получение пользователя")
    @Description("Выполняется запрос на получение пользователя с Id=2 " +
            "и проверяется, что его email оканчиваеся на @reqres.in, а ссылка на аватар соответствует шаблону")
    public void successObtainUser(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        GetUser user = given()
                .when()
                .get("/api/users/2")
                .then().log().all()
                .extract().body().jsonPath().getObject("data", GetUser.class);

        String regex = "https://reqres\\.in/img/faces/" + user.getId() + "-image\\.jpg";

        Assertions.assertEquals(2, user.getId());
        Assertions.assertTrue(user.getEmail().endsWith("@reqres.in"));
        Assertions.assertTrue(user.getAvatar().matches(regex));
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Пользователь не найден")
    @Description("Выполняется запрос на получение пользователя с Id=23 с ответным статус кодом 404")
    public void unSuccessObtainUser(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecError404());
        given()
                .when()
                .get("/api/users/23")
                .then().log().all();
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Получение списка пользователей с задержкой")
    @Description("Ввыполнятся запрос на получение списка пользователей с 1 страницы с задержкой ответа " +
            "и проверяется, что заявленное количество пользователей соответсвует полученному")
    public void checkDelayedObtainListOfUsers(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        GetUserList getUserList = given()
                .when()
                .get("/api/users?delay=3")
                .then().log().all()
                .extract().as(GetUserList.class);

        Assertions.assertEquals(12, getUserList.getTotal());
        Assertions.assertEquals(1, getUserList.getPage());
        Assertions.assertEquals(getUserList.getPer_page(), getUserList.getData().size());
    }
}
