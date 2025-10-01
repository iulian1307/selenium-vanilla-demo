package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.VeterinarianDto;
import com.qaitsolutions.data.VeterinariansXmlDto;
import com.qaitsolutions.pframe.core.assertion.SoftAssertions;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.utils.XmlUtils;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VeterinariansXmlPage {

    private static final By XML_CONTAINER = By.id("webkit-xml-viewer-source-xml");

    private final SoftAssertions assertions = new SoftAssertions();

    public VeterinariansXmlPage validateOnVeterinariansXmlPage() {
        var element = Wait.defaultWait()
                .until(f -> ExpectedConditions.presenceOfElementLocated(XML_CONTAINER));

        assertions.assertWithMessage("Validating that user is on Xml version of Veterinarians page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public void validateXmlHasValues(@NonNull final List<VeterinarianDto> expectedVeterinarians) {

        for (var vet : expectedVeterinarians) {
            if (vet.getSpecialties().isEmpty()) vet.setSpecialties(null);
        }

        var xmlString = Driver.getDriver().findElement(XML_CONTAINER).getDomProperty("innerHTML");

        VeterinariansXmlDto vets = XmlUtils
                .deserialize(xmlString, VeterinariansXmlDto.class);

        var actualVeterinarians = vets.getVets();

        var message = "Validating that veterinarians are as expected";
        assertions.assertWithMessage(message)
                .assertThat(expectedVeterinarians)
                .containsAtLeastElementsIn(actualVeterinarians);

        assertions.assertAll();
    }
}
