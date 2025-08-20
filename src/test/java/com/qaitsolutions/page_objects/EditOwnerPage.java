package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EditOwnerPage extends HeaderPage {

    private static final By PAGE_MESSAGE = By.xpath("//h2[contains(text(),'Owner')][not(contains(text(), 'New'))]");
    private static final By FIRST_NAME_INPUT = By.id("firstName");
    private static final By LAST_NAME_INPUT = By.id("lastName");
    private static final By ADDRESS_INPUT = By.id("address");
    private static final By CITY_INPUT = By.id("city");
    private static final By TELEPHONE_INPUT = By.id("telephone");
    private static final By UPDATE_OWNER_BUTTON = By.cssSelector("button.btn.btn-primary");

    public EditOwnerPage validateOnEditOwnerPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_MESSAGE));

        assertions.assertWithMessage("Validating that user is on Edit Owner page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public EditOwnerPage insertFirstName(final String name) {
        Log.info("Updating owner fist name to [%s]", name);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(FIRST_NAME_INPUT)).clear();

        Driver.getDriver()
                .findElement(FIRST_NAME_INPUT).sendKeys(name);

        return this;
    }

    public EditOwnerPage insertLastName(final String name) {
        Log.info("Updating owner last name to [%s]", name);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(LAST_NAME_INPUT)).clear();

        Driver.getDriver()
                .findElement(LAST_NAME_INPUT).sendKeys(name);

        return this;
    }

    public EditOwnerPage insertAddress(final String address) {
        Log.info("Updating owner address to [%s]", address);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADDRESS_INPUT)).clear();

        Driver.getDriver()
                .findElement(ADDRESS_INPUT).sendKeys(address);

        return this;
    }

    public EditOwnerPage insertCity(final String city) {
        Log.info("Updating owner city to [%s]", city);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(CITY_INPUT)).clear();

        Driver.getDriver()
                .findElement(CITY_INPUT).sendKeys(city);

        return this;
    }

    public EditOwnerPage insertTelephone(final String telephone) {
        Log.info("Updating owner telephone to [%s]", telephone);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(TELEPHONE_INPUT)).clear();

        Driver.getDriver()
                .findElement(TELEPHONE_INPUT).sendKeys(telephone);

        return this;
    }

    public OwnerPage updateOwner() {
        Log.info("Submitting updated owner");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(UPDATE_OWNER_BUTTON)).click();

        return new OwnerPage();
    }

    public EditOwnerPage updateOwnerFail() {
        Log.info("Submitting updated owner");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(UPDATE_OWNER_BUTTON)).click();

        return this;
    }

    public OwnerPage updateOwner(OwnerCreateDto owner) {
        this.insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone());

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this.updateOwner();
    }

    public EditOwnerPage updateOwnerFail(OwnerCreateDto owner) {
        this.insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone());

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this.updateOwnerFail();
    }
}
