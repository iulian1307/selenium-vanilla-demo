package com.qaitsolutions.selenium.wrapper.driver;

import com.qaitsolutions.selenium.wrapper.config.Config;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Setter @Getter
public class DriverConfig {
    private Browser browser;
    private String windowSize;
    private boolean headless;
    private Duration pageLoadTimeout;
    private String downloadDirectory;
    private boolean acceptInsecureCerts;
    private String driverPath;
    private String remoteUrl;

    public DriverConfig fromFile() {
        browser = Config.getBrowserType();
        windowSize = Config.getBrowserSize();
        headless = Config.isHeadless();
        downloadDirectory = Config.getDownloadPath();
        remoteUrl = Config.getRemoteUrl();

        return this;
    }
}
