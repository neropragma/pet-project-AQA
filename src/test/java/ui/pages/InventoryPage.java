package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import ui.base.BaseTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Tag("UI")
public class InventoryPage extends BaseTest {
    private final SelenideElement sideMenuButton = $x("//button[contains(text(),'Open Menu')]");
    private final SelenideElement sideLogoutLink = $x("//a[@id='logout_sidebar_link']");
    private final SelenideElement sortContainer = $x("//select[@class='product_sort_container']");
    private final ElementsCollection inventoryItemNames = $$x("//div[@class='inventory_item_name']");
    private final ElementsCollection inventoryItemPrices = $$x("//div[@class='inventory_item_price']");

    @Getter
    public enum SortOption{
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String optionValue;

        SortOption(String optionValue){
            this.optionValue = optionValue;
        }
    }

    public InventoryPage(){
        $x("//div[@class='app_logo']").shouldHave(text("Swag Labs"));
    }

    @Step("Открыть боковое меню и разлогиниться")
    public LoginPage logout(){
        sideMenuButton.click();
        sideLogoutLink.click();
        return new LoginPage();
    }

    @Step("Выбрать сортировку продуктов")
    public InventoryPage setProductSortOption(SortOption sortOption){
        sortContainer.selectOptionByValue(sortOption.getOptionValue());
        return this;
    }

    @Step("Проверить сортировку в алфавитном порядке")
    public InventoryPage checkSortNameAtoZ(){
        List<String> itemNames = inventoryItemNames.texts();
        List<String> itemNamesSorted = itemNames.stream().sorted().toList();
        Assertions.assertEquals(itemNamesSorted, itemNames);
        return this;
    }

    @Step("Проверить сортировку в обратном алфавитном порядке")
    public InventoryPage checkSortNameZtoA(){
        List<String> itemNames = inventoryItemNames.texts();
        List<String> itemNamesSorted = itemNames.stream().sorted(Comparator.reverseOrder()).toList();
        Assertions.assertEquals(itemNamesSorted, itemNames);
        return this;
    }

    @Step("Проверить сортировку по цене от меньшей к большей")
    public InventoryPage checkSortPriceLowToHigh(){
        List<Double> itemPrices = new ArrayList<>();
        inventoryItemPrices
                .texts()
                .forEach(x-> itemPrices.add(
                        Double.parseDouble(x.replace("$", ""))
                        )
                );
        List<Double> itemPricesSorted = itemPrices.stream().sorted().toList();
        Assertions.assertEquals(itemPricesSorted, itemPrices);
        return this;
    }

    @Step("Проверить сортировку по цене от большей к меньшей")
    public InventoryPage checkSortPriceHighToLow(){
        List<Double> itemPrices = new ArrayList<>();
        inventoryItemPrices
                .texts()
                .forEach(x-> itemPrices.add(
                                Double.parseDouble(x.replace("$", ""))
                        )
                );
        List<Double> itemPricesSorted = itemPrices.stream().sorted(Comparator.reverseOrder()).toList();
        Assertions.assertEquals(itemPricesSorted, itemPrices);
        return this;
    }

}
