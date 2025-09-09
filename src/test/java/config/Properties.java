package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

public class Properties  {
    public static final PropertiesInterface PROPERTIES = ConfigFactory.create(getPropertySource());

    private static Class<? extends PropertiesInterface> getPropertySource() {
        String env = System.getProperty("env");
        if (env == null || env.equals("null")) {
            return PropInterfaceTest.class;
        } else if (env.equals("test")) {
            return PropInterfaceTest.class;
        } else {
            throw new RuntimeException("Invalid value for system property 'env'! Expected one of:[null, 'test']");
        }
    }

    @LoadPolicy(Config.LoadType.MERGE)
    @Sources({"system:properties", "classpath:test.properties"})
    interface PropInterfaceTest extends PropertiesInterface {
    }

    public interface PropertiesInterface extends Config {

        // API tests properties
        @Key("reqresBaseUrl")
        String getReqresBaseUrl();

        // UI tests properties
        @Key("webBrowserName")
        String getBrowserName();

        @Key("webBrowserVersion")
        String getBrowserVersion();

        @Key("webBaseUrl")
        String getBaseUrl();

        @Key("webBrowserSize")
        String getBrowserSize();

        @Key("webPageLoadTimeout")
        Long getPageLoadTimeout();

        @Key("webTimeout")
        Long getTimeout();

        @Key("webIsHeadless")
        boolean isHeadless();

    }
}
