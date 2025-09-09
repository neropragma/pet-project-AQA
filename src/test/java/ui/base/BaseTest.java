package ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static config.Properties.PROPERTIES;

public class BaseTest {

    @BeforeAll
    public static void setUp(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = PROPERTIES.getBrowserName();
        Configuration.browserVersion = PROPERTIES.getBrowserVersion();
        Configuration.baseUrl = PROPERTIES.getBaseUrl();
        Configuration.browserSize = PROPERTIES.getBrowserSize();
        Configuration.pageLoadTimeout = PROPERTIES.getPageLoadTimeout();
        Configuration.timeout = PROPERTIES.getTimeout();
        Configuration.headless = PROPERTIES.isHeadless();
    }

    @AfterEach
    public void tearDown(){
        closeWebDriver();
    }

}
