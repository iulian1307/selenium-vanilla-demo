package com.qaitsolutions.page_objects;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FindOwnersPage extends HeaderPage {

    private static final By FIND_OWNERS_BUTTON = By.cssSelector("button.btn.btn-primary");
    private static final By FIND_OWNERS_INPUT = By.cssSelector("input#lastName");
    private static final By ADD_OWNER_BUTTON = By.cssSelector("a.btn.btn-primary");

    public FindOwnersPage validateOnFindOwnersPage() {
        var element = Wait.defaultWait()
                .until(f -> ExpectedConditions.elementToBeClickable(FIND_OWNERS_BUTTON));

        assertions.assertWithMessage("Validating that user is on Find Owners page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public AddOwnerPage addOwnerButton() {
        Log.info("Clicking on Add Owner button");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(ADD_OWNER_BUTTON))
                .click();

        return new AddOwnerPage();
    }

    public void findOwnerByLastName(String lastName) {
        Log.info("Finding owner by lastname: [" + lastName + "]");

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(FIND_OWNERS_INPUT))
                .sendKeys(lastName);

        Log.info("Clicking on Find Owners Button");

        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(FIND_OWNERS_BUTTON)).click();
    }
}
