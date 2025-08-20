package com.qaitsolutions.selenium.wrapper.driver;

import java.time.Duration;

public interface BrowserBuilder {

    BrowserBuilder windowSize(int width, int height);

    BrowserBuilder windowMaximized();

    BrowserBuilder headless(boolean flag);

    BrowserBuilder pageLoadTimeout(Duration duration);

    BrowserBuilder downloadDirectory(String directory);

    BrowserBuilder acceptInsecureCerts(boolean flag);

    BrowserBuilder driverPath(String path);

    BrowserBuilder remoteUrl(String url);

    void build();
}
