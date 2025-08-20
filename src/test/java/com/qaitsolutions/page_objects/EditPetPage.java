package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.PetDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;

public class EditPetPage extends AddPetPage {

    private static final By PAGE_MESSAGE = By.xpath("//h2[contains(text(),'Pet')]");
    private static final By UPDATE_PET_BUTTON = By.cssSelector("button.btn.btn-primary");

    public EditPetPage validateOnEditPetPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_MESSAGE));

        assertions.assertWithMessage("Validating that user is on Edit Pet page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public OwnerPage submitPet() {
        Log.info("Updating pet");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(UPDATE_PET_BUTTON)).click();

        return new OwnerPage();
    }

    public AddPetPage submitPetFail() {
        Log.info("Updating pet");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(UPDATE_PET_BUTTON)).click();

        return this;
    }

    public OwnerPage updatePetDetails(@NonNull final PetDto pet) {
        insertName(pet.getName());
        selectPetType(pet.getType());
        selectBirthDate(LocalDate.parse(pet.getBirthDate()));
        return submitPet();
    }
}
