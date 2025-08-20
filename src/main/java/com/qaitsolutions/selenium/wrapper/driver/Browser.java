package com.qaitsolutions.selenium.wrapper.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains all supported browsers
 */
@AllArgsConstructor
@Getter
public enum Browser {
    CHROME("chrome"),
    EDGE("edge"),
    FIREFOX("firefox");

    private final String identifier;
}
