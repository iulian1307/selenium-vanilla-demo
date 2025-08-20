package com.qaitsolutions.selenium.wrapper.wait;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.config.Config;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Wait {

    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(Config.getDefaultWait());

    public static WebDriverWait forSeconds(int seconds) {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
    }

    public static WebDriverWait defaultWait() {
        return new WebDriverWait(Driver.getDriver(), DEFAULT_DURATION);
    }
}
