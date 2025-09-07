package com.qaitsolutions.tests;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    @Parameters("browser")
    @BeforeSuite
    public void beforeSuite(String browser) {
        Log.info("Configure Driver");
        System.setProperty("selenium.browser.type", browser);
    }

    @AfterMethod
    public void after() {
        Driver.quit();
    }
}
