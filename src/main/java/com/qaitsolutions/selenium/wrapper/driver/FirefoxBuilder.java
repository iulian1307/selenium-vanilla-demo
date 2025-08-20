package com.qaitsolutions.selenium.wrapper.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.net.Urls;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirefoxBuilder implements BrowserBuilder {

    private FirefoxProfile profile;
    private URL remoteUrl;

    private final FirefoxOptions options = new FirefoxOptions();
    private boolean isMaximized = false;

    @Override
    public BrowserBuilder windowSize(int width, int height) {
        this.options.addArguments("--width=" + width);
        this.options.addArguments("--height=" + height);

        return this;
    }

    @Override
    public BrowserBuilder windowMaximized() {
        this.isMaximized = true;
        return this;
    }

    @Override
    public BrowserBuilder headless(boolean flag) {
        this.options.addArguments("--headless");
        return this;
    }

    @Override
    public BrowserBuilder pageLoadTimeout(Duration duration) {
        if (duration != null) this.options.setPageLoadTimeout(duration);
        return this;
    }

    @Override
    public BrowserBuilder downloadDirectory(String directory) {
        if (directory != null) {
            this.profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", directory);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", CommonMediaType.getAllWithSeparator(";"));
        }

        return this;
    }

    @Override
    public BrowserBuilder acceptInsecureCerts(boolean flag) {
        this.options.setAcceptInsecureCerts(flag);
        return this;
    }

    @Override
    public BrowserBuilder driverPath(String path) {
        if (path != null && !path.isEmpty()) System.setProperty("webdriver.gecko.driver", path);

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
        if (profile != null) options.setProfile(profile);

        var rmDriver = new ThreadLocal<WebDriver>();
        rmDriver.set(
                remoteUrl == null
                        ? new FirefoxDriver(options)
                        : new RemoteWebDriver(remoteUrl, options)
        );

        if (isMaximized)
            rmDriver.get().manage().window().maximize();

        Driver.setDriver(rmDriver.get());
    }
}
