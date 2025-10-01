package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.SpecialityDto;
import com.qaitsolutions.data.VeterinarianDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeterinariansPage extends HeaderPage {

    private static final By TABLE_NAME = By.id("veterinarians");
    private static final By JSON_LINK = By.cssSelector("a[href='/vets.json']");
    private static final By XML_LINK = By.cssSelector("a[href='/vets.xml']");

    private static final By TABLE_ROWS = By.cssSelector("tbody > tr");

    public VeterinariansPage validateOnVeterinariansPage() {
        var element = Wait.defaultWait()
                .until(f -> ExpectedConditions.visibilityOfElementLocated(TABLE_NAME));

        assertions.assertWithMessage("Validating that user is on Veterinarians page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public VeterinariansJsonPage openJsonPage() {
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(JSON_LINK)).click();
        return new VeterinariansJsonPage();
    }

    public VeterinariansXmlPage openXmlPage() {
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(XML_LINK)).click();
        return new VeterinariansXmlPage();
    }

    public void validateTableHasValues(@NonNull final List<VeterinarianDto> expectedVeterinarians) {
        var rows = Driver.getDriver().findElements(TABLE_ROWS);

        var actualVeterinarians = mapTable(rows);

        var message = "Validating that number of veterinarians is as expected [%s]";
        assertions.assertWithMessage(message, expectedVeterinarians.size())
                .assertThat(expectedVeterinarians.size())
                .isEqualTo(actualVeterinarians.size());

        assertions.assertAll();

        for (var expectedVeterinarian : expectedVeterinarians) {
            var expectedFirstName = expectedVeterinarian.getFirstName();
            var expectedLastName = expectedVeterinarian.getLastName();

            var actualVeterinarian = getVeterinarianFromList(expectedFirstName, expectedLastName, actualVeterinarians);

            message = "Validating that first name of veterinarian is as expected [%s]";
            assertions.assertWithMessage(message, expectedFirstName)
                    .assertThat(expectedFirstName)
                    .isEqualTo(actualVeterinarian.getFirstName());

            message = "Validating that last name of veterinarian is as expected [%s]";
            assertions.assertWithMessage(message, expectedLastName)
                    .assertThat(expectedLastName)
                    .isEqualTo(actualVeterinarian.getLastName());

            var expectedSpecialities = expectedVeterinarian.getSpecialties()
                    .stream()
                    .map(SpecialityDto::getName)
                    .toList();

            var actualSpecialities = actualVeterinarian.getSpecialties()
                    .stream()
                    .map(SpecialityDto::getName)
                    .toList();

            message = "Validating that specialities of veterinarian is as expected [%s]";
            assertions.assertWithMessage(message, expectedSpecialities)
                    .assertThat(expectedSpecialities)
                    .containsAtLeastElementsIn(actualSpecialities);
        }

        assertions.assertAll();
    }

    private VeterinarianDto getVeterinarianFromList(
            @NonNull final String firstName,
            @NonNull final String lastName,
            @NonNull final List<VeterinarianDto> veterinarians
    ) {
        return veterinarians.parallelStream()
                .filter(v -> v.getFirstName().equals(firstName) && v.getLastName().equals(lastName))
                .findFirst()
                .orElse(new VeterinarianDto());
    }

    private List<VeterinarianDto> mapTable(List<WebElement> rowsElements) {
        var veterinariansList = new ArrayList<VeterinarianDto>();

        for (WebElement row : rowsElements) {
            var veterinarianDto = new VeterinarianDto();

            var name = row.findElement(By.cssSelector("td:nth-child(1)")).getText();
            var nameSplit = name.split(" ");
            var firstName = nameSplit[0];
            var lastName = nameSplit[1];

            veterinarianDto.setFirstName(firstName);
            veterinarianDto.setLastName(lastName);

            var speciality = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            var specialityList = getSpecialitiesFromString(speciality);

            veterinarianDto.setSpecialties(specialityList);
            veterinariansList.add(veterinarianDto);
        }

        return veterinariansList;
    }

    private static ArrayList<SpecialityDto> getSpecialitiesFromString(String speciality) {
        var specialityList = new ArrayList<SpecialityDto>();

        if (!speciality.isEmpty() && !speciality.equals("none")) {
            var specialitySplit = speciality.contains(" ")
                    ? Arrays.asList(speciality.split(" "))
                    : List.of(speciality);

            for (var s : specialitySplit) {
                var specialityDto = new SpecialityDto();
                specialityDto.setName(s);

                specialityList.add(specialityDto);
            }
        }

        return specialityList;
    }
}
