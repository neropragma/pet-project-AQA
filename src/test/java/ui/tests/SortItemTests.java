package ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.base.BaseTest;
import ui.pages.InventoryPage;

import static ui.data.Users.standard_user;
import static ui.pages.InventoryPage.SortOption.*;
import static ui.pages.LoginPage.openLoginPage;

@Tag("UI")
public class SortItemTests extends BaseTest {

    @BeforeEach
    public void auth(){
        openLoginPage()
                .successLogin(standard_user);
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Проверка сортировки в алфавитном порядке")
    @Description("Выбор сортирорвки в алфавитном порядке и проверка соответствия")
    public void checkSortByNameAtoZ(){
        new InventoryPage()
                .setProductSortOption(NAME_A_TO_Z)
                .checkSortNameAtoZ();
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Проверка сортировки в обратном алфавитном порядке")
    @Description("Выбор сортирорвки в обратном алфавитном порядке и проверка соответствия")
    public void checkSortByNameZtoA(){
        new InventoryPage()
                .setProductSortOption(NAME_Z_TO_A)
                .checkSortNameZtoA();
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Проверка сортировки по цене от меньшей к большей")
    @Description("Выбор сортирорвки по цене от меньшей к большей и проверка соответствия")
    public void checkSortByPriceLowToHigh(){
        new InventoryPage()
                .setProductSortOption(PRICE_LOW_TO_HIGH)
                .checkSortPriceLowToHigh();
    }

    @Test
    @Owner("m.radkevich")
    @DisplayName("Проверка сортировки по цене от большей к меньшей")
    @Description("Выбор сортирорвки по цене от большей к меньшей и проверка соответствия")
    public void checkSortByPriceHighToLow(){
        new InventoryPage()
                .setProductSortOption(PRICE_HIGH_TO_LOW)
                .checkSortPriceHighToLow();
    }
}
