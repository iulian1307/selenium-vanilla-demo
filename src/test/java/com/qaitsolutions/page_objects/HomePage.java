package com.qaitsolutions.page_objects;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends HeaderPage {

    private static final By WELCOME_MESSAGE = By.xpath("//h2[text()='Welcome']");

    public HeaderPage validateOnHomepage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(WELCOME_MESSAGE));

        assertions.assertWithMessage("Validating that user is on Homepage")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }
}
