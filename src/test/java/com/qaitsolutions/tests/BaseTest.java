package com.qaitsolutions.tests;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.driver.DriverBuilder;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.testng.annotations.*;

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
