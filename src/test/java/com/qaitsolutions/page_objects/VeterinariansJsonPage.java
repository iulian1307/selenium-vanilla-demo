package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.VeterinarianDto;
import com.qaitsolutions.data.VeterinariansJsonDto;
import com.qaitsolutions.pframe.core.assertion.SoftAssertions;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.utils.JsonUtils;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VeterinariansJsonPage {

    private static final By JSON_CONTAINER = By.cssSelector("div.json-formatter-container");
    private static final By JSON_STRING = By.cssSelector("pre");

    private final SoftAssertions assertions = new SoftAssertions();

    public VeterinariansJsonPage validateOnVeterinariansJsonPage() {
        var element = Wait.defaultWait()
                .until(f -> ExpectedConditions.presenceOfElementLocated(JSON_CONTAINER));

        assertions.assertWithMessage("Validating that user is on Json version of Veterinarians page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public void validateJsonHasValues(@NonNull final List<VeterinarianDto> expectedVeterinarians) {
        var jsonString = Driver.getDriver().findElement(JSON_STRING).getText();

        List<VeterinarianDto> actualVeterinarians = JsonUtils
                .deserialize(jsonString, VeterinariansJsonDto.class)
                .getVets();

        var message = "Validating that veterinarians are as expected";
        assertions.assertWithMessage(message)
                .assertThat(actualVeterinarians)
                .containsAtLeastElementsIn(expectedVeterinarians);

        assertions.assertAll();
    }
}
