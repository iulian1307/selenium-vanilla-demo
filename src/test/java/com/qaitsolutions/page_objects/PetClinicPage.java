package com.qaitsolutions.page_objects;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.driver.DriverBuilder;

public class PetClinicPage {

//    private static final String HOME_URL = "http://host.docker.internal:8040";
//    private static final String HOME_URL = "http://www.google.com";
    private static final String HOME_URL = "http://127.0.0.1:8040";

    public HomePage openWebpage() {
        Log.info("Navigating to Homepage");

        new DriverBuilder().fromConfigFile().build();
        Driver.getDriver().navigate().to(HOME_URL);

        return new HomePage();
    }
}
