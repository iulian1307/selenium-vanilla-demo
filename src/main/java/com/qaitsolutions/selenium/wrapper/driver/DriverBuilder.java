package com.qaitsolutions.selenium.wrapper.driver;

import com.qaitsolutions.selenium.wrapper.config.Config;
import lombok.Setter;

@Setter
public class DriverBuilder {

    private DriverConfig config;

    public DriverBuilder fromConfigFile() {
        this.config = new DriverConfig().fromFile();
        return this;
    }

    public void build() {
        if (config == null) throw new RuntimeException("No config was set to build Driver");

        BrowserBuilder builder = switch (config.getBrowser()) {
            case EDGE -> new EdgeBuilder();
            case CHROME -> new ChromeBuilder();
            case FIREFOX -> new FirefoxBuilder();
        };

        builder.headless(config.isHeadless())
                .pageLoadTimeout(config.getPageLoadTimeout())
                .downloadDirectory(config.getDownloadDirectory())
                .acceptInsecureCerts(config.isAcceptInsecureCerts())
                .driverPath(config.getDriverPath());

        if (Config.getBrowserSize().equals("max")) {
            builder.windowMaximized();
        } else {
            var size = Config.getBrowserSize();
            var width = Integer.parseInt(size.substring(0, size.indexOf('x')));
            var height = Integer.parseInt(size.substring(size.indexOf('x') + 1));

            builder.windowSize(width, height);
        }

        if (!Config.getRemoteUrl().isEmpty()) {
            builder.remoteUrl(Config.getRemoteUrl());
        }

        builder.build();
    }
}
