package com.qaitsolutions.page_objects;

import com.qaitsolutions.pframe.core.assertion.SoftAssertions;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderPage {
    private static final By HOME_BUTTON = By.cssSelector("a[title='home page']");
    private static final By FIND_OWNERS_BUTTON = By.cssSelector("a[title='find owners']");
    private static final By VETERINARIANS_BUTTON = By.cssSelector("a[title='veterinarians']");
    private static final By ERROR_BUTTON = By.cssSelector("a[title*='trigger']");

    protected final SoftAssertions assertions = new SoftAssertions();

    public FindOwnersPage openFindOwnersPage() {
        Log.info("Opening Find Owners page");

        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(FIND_OWNERS_BUTTON)).click();

        return new FindOwnersPage();
    }

    public HomePage openHomePage() {
        Log.info("Opening Home page");

        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(HOME_BUTTON)).click();

        return new HomePage();
    }

    public VeterinariansPage openVeterinariansPage() {
        Log.info("Opening Veterinarians page");

        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(VETERINARIANS_BUTTON)).click();

        return new VeterinariansPage();
    }

    public ErrorPage openErrorPage() {
        Log.info("Opening Error page");

        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(ERROR_BUTTON)).click();

        return new ErrorPage();
    }

    public void validateAll() {
        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        assertions.assertAll();
    }
}
