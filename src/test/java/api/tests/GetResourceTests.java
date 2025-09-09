package api.tests;

import api.models.resource.Resource;
import api.models.resource.ResourceList;
import api.specs.Specifications;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Tag("API")
public class GetResourceTests {
    private final static String URL = "https://reqres.in";

    @Test
    @Owner("m.radkevich")
    @DisplayName("Получение списка ресурсов")
    @Description("Ввыполнятся запрос на получение списка ресурсов с 1 страницы " +
            "и проверяется, что заявленное количество ресурсов соответсвует полученному")
    public void checkObtainListOfResources(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        ResourceList resourceList = given()
                .when()
                .get("/api/unknown")
                .then().log().all()
                .extract().as(ResourceList.class);

        Assertions.assertEquals(12, resourceList.getTotal());
        Assertions.assertEquals(1, resourceList.getPage());
        Assertions.assertEquals(resourceList.getPer_page(), resourceList.getData().size());
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Получение ресурса")
    @Description("Выполняется запрос на получение ресурса(цвета) с Id=2 " +
            "и проверяется, что он имеет обозначения по HEX и Panton стандартам")
    public void successObtainResource(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecOk200());
        Resource resource = given()
                .when()
                .get("/api/unknown/2")
                .then().log().all()
                .extract().body().jsonPath().getObject("data", Resource.class);

        String regexHEX = "#[\\dA-F]{6}";
        String regexPantone = "\\d{2}-\\d{4}";

        Assertions.assertEquals(2, resource.getId());
        Assertions.assertTrue(resource.getColor().matches(regexHEX));
        Assertions.assertTrue(resource.getPantone_value().matches(regexPantone));
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Ресурс не найден")
    @Description("Выполняется запрос на получение ресурса с Id=23 с ответным статус кодом 404")
    public void unSuccessObtainResource(){
        Specifications.installSpecifications(Specifications.requestSpec(), Specifications.responseSpecError404());
        given()
                .when()
                .get("/api/unknown/23")
                .then().log().all();
    }
}
