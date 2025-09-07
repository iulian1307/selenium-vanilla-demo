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
import java.util.ArrayList;

public class AddVisitPage extends HeaderPage {

    private static final By SUBMIT_BUTTON = By.cssSelector("button[type='submit']");
    private static final By DESCRIPTION_INPUT = By.id("description");
    private static final By DATE_INPUT = By.id("date");

    private static final By DESCRIPTION_ERROR_TEXT = By.cssSelector("form#visit span.help-inline");

    private static final By PREVIOUS_VISITS_TABLE = By.cssSelector("table[aria-describedby='previousVisits'] tr");

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
        Wait.defaultWait().until(ExpectedConditions
                .elementToBeClickable(SUBMIT_BUTTON)).click();

        Wait.forSeconds(2);

        return new OwnerPage();
    }

    public AddVisitPage submitVisitFail() {
        Wait.defaultWait()
                .until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();

        return this;
    }

    public AddVisitPage validatePreviousVisitIsCorrect(VisitDto visit) {

        var tableRows = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PREVIOUS_VISITS_TABLE));

        var actualVisits = new ArrayList<VisitDto>();

        for (int i = 1; i < tableRows.size(); i++) {
            var columns = tableRows.get(i).findElements(By.cssSelector("td"));
            var visitDto = new VisitDto();

            for (int j = 0; j < columns.size(); j++) {
                if (j == 0) {
                    var date = columns.get(j).getText();
                    date = date.replace("/", "-");

                    visitDto.setDate(date);
                } else {
                    visitDto.setDescription(columns.get(j).getText());
                }
            }

            actualVisits.add(visitDto);
        }

        assertions.assertWithMessage("Validating that previous visits contains", visit.toString())
                .assertThat(actualVisits)
                .contains(visit);

        assertions.assertAll();

        return this;
    }

    public AddVisitPage checkDescriptionEmptyFieldError() {

        final var expectedErrorMessage = "must not be empty";
        Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION_ERROR_TEXT));
        final var actualErrorMessage = Driver.getDriver().findElement(DESCRIPTION_ERROR_TEXT).getText();

        final var message = "Validating that error message is [%s]";
        assertions.assertWithMessage(message, expectedErrorMessage)
                .assertThat(actualErrorMessage)
                .isEqualTo(expectedErrorMessage);

        return this;
    }
}
