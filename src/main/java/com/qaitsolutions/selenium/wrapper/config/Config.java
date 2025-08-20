package com.qaitsolutions.selenium.wrapper.config;

import com.qaitsolutions.pframe.core.utils.PropertiesUtil;
import com.qaitsolutions.selenium.wrapper.driver.Browser;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Predicate;

public class Config {
    private static final String CONFIG_PATH = "src/main/resources/config";
    private static final String CONFIG_FILENAME = "selenium.properties";
    private static final String BROWSER_TYPE_KEY = "selenium.browser.type";
    private static final String BROWSER_SIZE_KEY = "selenium.browser.size";
    private static final String BROWSER_HEADLESS_KEY = "selenium.browser.is_headless";
    private static final String DOWNLOAD_PATH = "selenium.download.path";
    private static final String WAIT_KEY = "selenium.wait";
    private static final String REMOTE_URL_KEY = "selenium.remote.url";

    private static final String BROWSER_TYPE_DEFAULT = Browser.CHROME.getIdentifier();
    private static final String BROWSER_SIZE_DEFAULT = "max";
    private static final String BROWSER_HEADLESS_DEFAULT = "false";
    private static final String DOWNLOAD_PATH_DEFAULT = "target/downloads";
    private static final String WAIT_DEFAULT = "15";

    private static Properties properties;

    private Config() {
    }

    /**
     * Gets browser size from system parameter. If the system parameter is not present,
     * it will attempt to retrieve from properties file.
     *
     * @return browser size as {@link String}
     */
    public static String getBrowserSize() {
        final String size = System.getProperty(BROWSER_SIZE_KEY, "");
        if (!size.isEmpty()) return size;

        instantiateProperties();
        return properties.getProperty(BROWSER_SIZE_KEY, BROWSER_SIZE_DEFAULT);
    }

    /**
     * Gets browser type from system parameter. If the system parameter is not present,
     * it will attempt to retrieve from properties file. It will ultimately convert it
     * to {@link Browser}
     *
     * @return browser type as {@link Browser}
     */
    public static Browser getBrowserType() {
        String type = System.getProperty(BROWSER_TYPE_KEY, "");

        if (type.isEmpty()) {
            instantiateProperties();
            type = properties.getProperty(BROWSER_TYPE_KEY, BROWSER_TYPE_DEFAULT);
        }

        final String browserType = type;

        final Predicate<Browser> browserPredicate = b -> b.getIdentifier().equals(browserType);

        return Arrays.stream(Browser.values())
                .filter(browserPredicate)
                .findAny()
                .orElse(Browser.CHROME);
    }

    /**
     * Gets is headless from system parameter. If the system parameter is not present,
     * it will attempt to retrieve from properties file.
     *
     * @return is headless as boolean
     */
    public static boolean isHeadless() {
        final String isHeadless = System.getProperty(BROWSER_HEADLESS_KEY, "");
        if (!isHeadless.isEmpty()) return Boolean.parseBoolean(isHeadless);

        instantiateProperties();
        return Boolean.parseBoolean(
                properties.getProperty(BROWSER_HEADLESS_KEY, BROWSER_HEADLESS_DEFAULT)
        );
    }

    /**
     * Gets default wait from system parameter. If the system parameter is not present,
     * it will attempt to retrieve from properties file.
     *
     * @return default wait value as int
     */
    public static int getDefaultWait() {
        final String wait = System.getProperty(WAIT_KEY, "");
        if (!wait.isEmpty()) return Integer.parseInt(wait);

        instantiateProperties();
        return Integer.parseInt(properties.getProperty(WAIT_KEY, WAIT_DEFAULT));
    }

    /**
     * Gets default download path from system parameter. If the system parameter is not present,
     * it will attempt to retrieve from properties file.
     *
     * @return default wait value as int
     */
    public static String getDownloadPath() {
        final String path = System.getProperty(DOWNLOAD_PATH, DOWNLOAD_PATH_DEFAULT);
        if (!path.isEmpty()) return path;

        instantiateProperties();
        return properties.getProperty(DOWNLOAD_PATH, DOWNLOAD_PATH_DEFAULT);
    }

    /**
     * Gets remote url from system parameter. If the system parameter is not present it will attempt to retrieve from
     * properties file. If value is not set in properties file it will return an empty string.
     *
     * @return default wait value as String
     */
    public static String getRemoteUrl() {
        var url = System.getProperty(REMOTE_URL_KEY, "");
        if (!url.isEmpty()) return url;

        instantiateProperties();
        url = properties.getProperty(REMOTE_URL_KEY, "");

        return url;
    }

    /**
     * Helper method that instantiates {@link Properties} object from file
     */
    private static void instantiateProperties() {
        if (properties != null) return;

        final String propertiesPath = CONFIG_PATH + "/" + CONFIG_FILENAME;
        properties = PropertiesUtil.read(propertiesPath);
    }
}
