package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.data.OwnersTableRow;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class OwnersListPage extends HeaderPage {

    private static final By OWNERS_TABLE = By.cssSelector("table#ownersTable");
    private static final By TABLE_ROWS = By.cssSelector("tbody > tr");

    public static boolean isOnPage() {
        try {
            Wait.forSeconds(3)
                    .until(ExpectedConditions.visibilityOfElementLocated(OWNERS_TABLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public OwnersListPage validateOnOwnersListPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(OWNERS_TABLE));

        assertions.assertWithMessage("Validating user is on Owners List page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public OwnerPage selectUserFromTable(OwnerCreateDto owner) {

        var fullName = owner.getFirstName() + " " + owner.getLastName();
        Log.info("Selecting user [%s] from list", fullName);

        var rowsElements = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(TABLE_ROWS));

        var mappedRows = mapTable(rowsElements);

        WebElement found = null;

        for (OwnersTableRow row : mappedRows) {
            if (row.getName().equals(fullName)) {
                found = row.getLink();
                found.click();
                break;
            }
        }

        if (found == null) {
            var message = String.format("Owner [%s] was not found in Owners List page", fullName);
            assertions.assertWithMessage(message).hardFail();
        }

        return new OwnerPage();
    }

    private List<OwnersTableRow> mapTable(List<WebElement> rowsElements) {
        var list = new ArrayList<OwnersTableRow>();

        for (WebElement row : rowsElements) {
            var owner = new OwnersTableRow();

            var link = row.findElement(By.cssSelector("td:nth-child(1) > a"));
            var name = link.getText();
            var address = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            var city = row.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
            var telephone = row.findElement(By.cssSelector("td:nth-child(4)")).getText().trim();

            var petsConcatenated = row.findElement(By.cssSelector("td:nth-child(5)")).getText().trim();
            var listOfPets = !petsConcatenated.contains(" ")
                    ? List.of(petsConcatenated)
                    : List.of(petsConcatenated.split(" "));

            owner.setName(name);
            owner.setLink(link);
            owner.setAddress(address);
            owner.setCity(city);
            owner.setTelephone(telephone);
            owner.setPets(listOfPets);

            list.add(owner);
        }

        return list;
    }
}
