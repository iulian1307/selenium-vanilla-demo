package com.qaitsolutions.selenium.wrapper.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.net.Urls;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class EdgeBuilder implements BrowserBuilder {

    private final EdgeOptions options = new EdgeOptions();
    private URL remoteUrl;

    @Override
    public BrowserBuilder windowSize(int width, int height) {
        options.addArguments(String.format("--window-size=%s,%s", width, height));

        return this;
    }

    @Override
    public BrowserBuilder windowMaximized() {
        options.addArguments("start-maximized");
        return this;
    }

    @Override
    public BrowserBuilder headless(boolean flag) {
        options.addArguments("--headless");
        return this;
    }

    @Override
    public BrowserBuilder pageLoadTimeout(Duration duration) {
        if (duration != null) options.setPageLoadTimeout(duration);
        return this;
    }

    @Override
    public BrowserBuilder downloadDirectory(String directory) {
        if (directory != null) {
            var preferences = new HashMap<String, Object>();
            preferences.put("download.default_directory", directory);
            options.setExperimentalOption("prefs", preferences);
        }

        return this;
    }

    @Override
    public BrowserBuilder acceptInsecureCerts(boolean flag) {
        options.setAcceptInsecureCerts(flag);
        return this;
    }

    @Override
    public BrowserBuilder driverPath(String path) {
        if (path != null) System.setProperty("webdriver.edge.driver", path);
        return this;
    }

    @Override
    public BrowserBuilder remoteUrl(String url) {
        try {
            remoteUrl = Urls.from(url).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url for Remote", e);
        }

        return this;
    }

    @Override
    public void build() {
        var rmDriver = new ThreadLocal<WebDriver>();
        rmDriver.set(
                remoteUrl == null
                        ? new EdgeDriver(options)
                        : new RemoteWebDriver(remoteUrl, options)
        );

        //TODO add parallel execution in testng.xml
        //TODO docker compose with sel hub/chrome/firefox/edge + env
        //TODO change report type to multi

        Driver.setDriver(rmDriver.get());
    }
}
