package com.qaitsolutions.page_objects;

import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;

public class DatePickerModal {

    private static final By CURRENT_YEAR = By.cssSelector("input.numInput.cur-year");
    private static final By INCREASE_YEAR = By.cssSelector("span.arrowUp");
    private static final By DECREASE_YEAR = By.cssSelector("span.arrowDown");

    private static final By MONTH_DROPDOWN = By.cssSelector("select.flatpickr-monthDropdown-months");

    private static final By DAYS_CALENDAR = By.cssSelector("span[class='flatpickr-day']");

    public void selectDateFromPicker(@NonNull final LocalDate date) {
        this.selectYearFromDatePicker(date.getYear());
        this.selectMonthFromDatePicker(date.getMonthValue());
        this.selectDayFromDatePicker(date.getDayOfMonth());
    }

    private void selectYearFromDatePicker(int year) {

        var yearValue = Driver.getDriver().findElement(CURRENT_YEAR).getDomProperty("value");
        if (yearValue == null || yearValue.isEmpty()) throw new AssertionError("Year couldn't be found");

        var currentYear = Integer
                .parseInt(yearValue);

        var diffYear = currentYear - year;
        var absDiffYear = Math.abs(diffYear);

        var locatorToUse = diffYear > 0 ? DECREASE_YEAR : INCREASE_YEAR;

        for (int i = 0; i < absDiffYear; i++)
            Driver.getDriver().findElement(locatorToUse).click();
    }

    private void selectMonthFromDatePicker(int month) {
        new Select(Driver.getDriver().findElement(MONTH_DROPDOWN))
                .selectByIndex(month - 1);
    }

    private void selectDayFromDatePicker(int day) {
        Driver.getDriver().findElements(DAYS_CALENDAR).get(day - 1).click();
    }
}
