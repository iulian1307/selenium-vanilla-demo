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
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;

public class AddPetPage extends HeaderPage {

    private static final By PAGE_MESSAGE = By.xpath("//h2[contains(text(),'New  Pet')]");
    private static final By NAME_INPUT = By.id("name");
    private static final By BIRT_DATE_INPUT = By.id("birthDate");
    private static final By TYPE_OPTION = By.id("type");
    private static final By ADD_PET_BUTTON = By.cssSelector("button.btn.btn-primary");

    private static final By NAME_ERROR_TEXT =
            By.cssSelector("form#pet div:nth-child(2) span.help-inline");

    private static final By BIRTH_DATE_ERROR_TEXT =
            By.cssSelector("form#pet div:nth-child(3) span.help-inline");

    private static final By TYPE_ERROR_TEXT =
            By.cssSelector("form#pet div:nth-child(4) span.help-inline");

    public AddPetPage validateOnAddPetPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_MESSAGE));

        assertions.assertWithMessage("Validating that user is on Add Pet page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public AddPetPage insertName(final String name) {
        Log.info("Inserting pet name [%s]", name);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(NAME_INPUT)).clear();

        Driver.getDriver()
                .findElement(NAME_INPUT).sendKeys(name);

        return this;
    }

    public AddPetPage selectBirthDate(@NonNull final LocalDate date) {
        Log.info("Selecting pet birth date [%s]", date.toString());
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(BIRT_DATE_INPUT)).click();

        new DatePickerModal().selectDateFromPicker(date);

        return this;
    }

    public AddPetPage selectPetType(@NonNull final String type) {
        Log.info("Selecting pet type [%s]", type);

        new Select(Driver.getDriver().findElement(TYPE_OPTION))
                .selectByValue(type);

        return this;
    }

    public OwnerPage submitPet() {
        Log.info("Submitting new pet");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADD_PET_BUTTON)).click();

        return new OwnerPage();
    }

    public AddPetPage submitPetFail() {
        Log.info("Submitting new pet");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADD_PET_BUTTON)).click();

        return this;
    }

    public OwnerPage addPet(PetDto pet) {
        insertName(pet.getName());
        selectPetType(pet.getType());
        selectBirthDate(LocalDate.parse(pet.getBirthDate()));
        return submitPet();
    }

    public AddPetPage checkNameFieldIsRequired() {
        this.checkIsRequiredFieldError(NAME_ERROR_TEXT);
        return this;
    }

    public AddPetPage checkBirthDateFieldIsRequired() {
        this.checkIsRequiredFieldError(BIRTH_DATE_ERROR_TEXT);
        return this;
    }

    public AddPetPage checkTypeFieldIsRequired() {
        this.checkIsRequiredFieldError(TYPE_ERROR_TEXT);
        return this;
    }

    private void checkIsRequiredFieldError(@NonNull final By fieldLocator) {
        final var expectedErrorMessage = "is required";
        Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));
        final var actualErrorMessage = Driver.getDriver().findElement(fieldLocator).getText();

        final var message = "Validating that error message is [%s]";
        assertions.assertWithMessage(message, expectedErrorMessage)
                .assertThat(actualErrorMessage)
                .isEqualTo(expectedErrorMessage);
    }
}
