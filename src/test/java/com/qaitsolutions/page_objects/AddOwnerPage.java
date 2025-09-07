package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AddOwnerPage extends HeaderPage {

    private static final By PAGE_MESSAGE = By.xpath("//h2[contains(text(),'New  Owner')]");
    private static final By FIRST_NAME_INPUT = By.id("firstName");
    private static final By LAST_NAME_INPUT = By.id("lastName");
    private static final By ADDRESS_INPUT = By.id("address");
    private static final By CITY_INPUT = By.id("city");
    private static final By TELEPHONE_INPUT = By.id("telephone");
    private static final By ADD_OWNER_BUTTON = By.cssSelector("button.btn.btn-primary");

    private static final By FIRST_NAME_ERROR_TEXT =
            By.cssSelector("form#add-owner-form > div > div:nth-child(1) span.help-inline");

    private static final By LAST_NAME_ERROR_TEXT =
            By.cssSelector("form#add-owner-form > div > div:nth-child(2) span.help-inline");

    private static final By ADDRESS_ERROR_TEXT =
            By.cssSelector("form#add-owner-form > div > div:nth-child(3) span.help-inline");

    private static final By CITY_ERROR_TEXT =
            By.cssSelector("form#add-owner-form > div > div:nth-child(4) span.help-inline");

    private static final By TELEPHONE_ERROR_TEXT =
            By.cssSelector("form#add-owner-form > div > div:nth-child(5) span.help-inline");

    public AddOwnerPage assertOnAddOwnerPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PAGE_MESSAGE));

        assertions.assertWithMessage("Validating user is on Add Owner page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public AddOwnerPage insertFirstName(final String name) {
        Log.info("Inserting owner first name [%s]", name);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(FIRST_NAME_INPUT)).clear();

        Driver.getDriver()
                .findElement(FIRST_NAME_INPUT).sendKeys(name);

        return this;
    }

    public AddOwnerPage insertLastName(final String name) {
        Log.info("Inserting owner last name [%s]", name);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(LAST_NAME_INPUT)).clear();

        Driver.getDriver()
                .findElement(LAST_NAME_INPUT).sendKeys(name);

        return this;
    }

    public AddOwnerPage insertAddress(final String address) {
        Log.info("Inserting owner address [%s]", address);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADDRESS_INPUT)).clear();

        Driver.getDriver()
                .findElement(ADDRESS_INPUT).sendKeys(address);

        return this;
    }

    public AddOwnerPage insertCity(final String city) {
        Log.info("Inserting owner city [%s]", city);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(CITY_INPUT)).clear();

        Driver.getDriver()
                .findElement(CITY_INPUT).sendKeys(city);

        return this;
    }

    public AddOwnerPage insertTelephone(final String telephone) {
        Log.info("Inserting owner telephone [%s]", telephone);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(TELEPHONE_INPUT)).clear();

        Driver.getDriver()
                .findElement(TELEPHONE_INPUT).sendKeys(telephone);

        return this;
    }

    public OwnerPage submitOwner() {
        Log.info("Submitting new owner");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADD_OWNER_BUTTON)).click();

        return new OwnerPage();
    }

    public AddOwnerPage submitOwnerFail() {
        Log.info("Submitting new owner");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADD_OWNER_BUTTON)).click();

        return this;
    }

    public OwnerPage addOwner(OwnerCreateDto owner) {
        this.insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone());

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this.submitOwner();
    }

    public AddOwnerPage addOwnerFail(OwnerCreateDto owner) {
        this.insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone());

        final var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this.submitOwnerFail();
    }

    public AddOwnerPage checkFirstNameEmptyFieldError() {
        this.checkEmptyFieldError(FIRST_NAME_ERROR_TEXT);
        return this;
    }

    public AddOwnerPage checkLastNameEmptyFieldError() {
        this.checkEmptyFieldError(LAST_NAME_ERROR_TEXT);
        return this;
    }

    public AddOwnerPage checkAddressEmptyFieldError() {
        this.checkEmptyFieldError(ADDRESS_ERROR_TEXT);
        return this;
    }

    public AddOwnerPage checkCityEmptyFieldError() {
        this.checkEmptyFieldError(CITY_ERROR_TEXT);
        return this;
    }

    public AddOwnerPage checkTelephoneFieldError() {
        final var numericValueError = "numeric value out of bounds (<10 digits>.<0 digits> expected)";
        final var emptyFieldError = "must not be empty";

        var expectedErrorMessages = List.of(numericValueError, emptyFieldError);

        Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(TELEPHONE_ERROR_TEXT));
        final var actualErrorMessage = Driver.getDriver().findElement(TELEPHONE_ERROR_TEXT).getText();

        Log.warn(
                "Known issue, telephone field throws one of two errors randomly when field is left empty. %s",
                expectedErrorMessages
        );

        final var message = "Validating that error message is in %s";
        assertions.assertWithMessage(message, expectedErrorMessages)
                .assertThat(expectedErrorMessages)
                .contains(actualErrorMessage);

        return this;
    }

    public AddOwnerPage checkTelephoneEmptyFieldError() {
        this.checkEmptyFieldError(TELEPHONE_ERROR_TEXT);
        return this;
    }

    public AddOwnerPage checkTelephoneNumericValueError() {
        final var expectedErrorMessage = "numeric value out of bounds (<10 digits>.<0 digits> expected)";

        Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(TELEPHONE_ERROR_TEXT));
        final var actualErrorMessage = Driver.getDriver().findElement(TELEPHONE_ERROR_TEXT).getText();

        final var message = "Validating that error message is [%s]";
        assertions.assertWithMessage(message, expectedErrorMessage)
                .assertThat(actualErrorMessage)
                .isEqualTo(expectedErrorMessage);

        return this;
    }

    private void checkEmptyFieldError(@NonNull final By fieldLocator) {
        final var expectedErrorMessage = "must not be empty";
        Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));
        final var actualErrorMessage = Driver.getDriver().findElement(fieldLocator).getText();

        final var message = "Validating that error message is [%s]";
        assertions.assertWithMessage(message, expectedErrorMessage)
                .assertThat(actualErrorMessage)
                .isEqualTo(expectedErrorMessage);
    }
}
