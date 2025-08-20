package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.VisitDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;

public class AddVisitPage extends HeaderPage {

    private static final By SUBMIT_BUTTON = By.cssSelector("button[type='submit']");
    private static final By DESCRIPTION_INPUT = By.id("description");
    private static final By DATE_INPUT = By.id("date");

    public AddVisitPage validateOnAddVisitPage() {
        var element = Wait.defaultWait()
                .until(f -> ExpectedConditions.elementToBeClickable(DATE_INPUT));

        assertions.assertWithMessage("Validating that user is on Add Visit page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public OwnerPage addVisit(VisitDto visit) {
        Log.info("Adding visit %s", visit);

        this.insertDescription(visit.getDescription());
        this.selectDate(LocalDate.parse(visit.getDate()));

        return this.submitVisit();
    }

    public AddVisitPage insertDescription(@NonNull final String description) {
        Log.info("Inserting visit description [%s]", description);

        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(DESCRIPTION_INPUT)).clear();

        Driver.getDriver()
                .findElement(DESCRIPTION_INPUT).sendKeys(description);

        return this;
    }

    public AddVisitPage selectDate(@NonNull final LocalDate date) {
        Log.info("Selecting visit date [%s]", date.toString());
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(DATE_INPUT)).click();

        new DatePickerModal().selectDateFromPicker(date);

        return this;
    }

    public OwnerPage submitVisit() {
        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();

        Wait.forSeconds(2);

        return new OwnerPage();
    }
}
