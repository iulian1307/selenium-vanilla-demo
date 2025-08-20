package com.qaitsolutions.page_objects;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.data.OwnerFromPageDto;
import com.qaitsolutions.data.PetDto;
import com.qaitsolutions.data.VisitDto;
import com.qaitsolutions.pframe.core.logging.Log;
import com.qaitsolutions.selenium.wrapper.driver.Driver;
import com.qaitsolutions.selenium.wrapper.wait.Sleep;
import com.qaitsolutions.selenium.wrapper.wait.Wait;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OwnerPage extends HeaderPage {

    private static final By PAGE_MESSAGE = By.id("ownerInformation");
    private static final By EDIT_OWNER_BUTTON = By.cssSelector("a[href*='/edit']");
    private static final By ADD_NEW_PET_BUTTON = By.cssSelector("a[href*='/pets/new']");

    private static final By OWNER_NAME_FIELD = By.cssSelector("td[headers='name'] strong");
    private static final By OWNER_ADDRESS_FIELD = By.cssSelector("td[headers='address']");
    private static final By OWNER_CITY_FIELD = By.cssSelector("td[headers='city']");
    private static final By OWNER_TELEPHONE_FIELD = By.cssSelector("td[headers='telephone']");

    private static final By PETS_TABLES =
            By.cssSelector("table[class='table table-striped'][aria-describedby='petsAndVisits'] > tbody > tr");

    private List<PetDto> currentPets = new ArrayList<>();

    public static boolean isOnPage() {
        try {
            Wait.forSeconds(3)
                    .until(ExpectedConditions.visibilityOfElementLocated(EDIT_OWNER_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public OwnerPage validateOnOwnerPage() {
        var element = Wait.defaultWait()
                .until(ExpectedConditions.visibilityOfElementLocated(PAGE_MESSAGE));

        assertions.assertWithMessage("Validating that user is on Owner page")
                .assertThat(element)
                .isNotNull();

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }

    public EditOwnerPage editOwner() {
        Log.info("Opening Edit Owner page");
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(EDIT_OWNER_BUTTON)).click();

        return new EditOwnerPage();
    }

    public AddPetPage addNewPet() {
        Log.info("Opening Add Pet page");
        Wait.defaultWait().until(ExpectedConditions.elementToBeClickable(ADD_NEW_PET_BUTTON)).click();

        return new AddPetPage();
    }

    public String getOwnerName() {
        return Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(OWNER_NAME_FIELD)).getText();
    }

    public String getOwnerAddress() {
        return Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(OWNER_ADDRESS_FIELD)).getText();
    }

    public String getOwnerCity() {
        return Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(OWNER_CITY_FIELD)).getText();
    }

    public String getOwnerTelephone() {
        return Wait.defaultWait().until(ExpectedConditions.visibilityOfElementLocated(OWNER_TELEPHONE_FIELD)).getText();
    }

    public List<PetDto> getAllPets() {

        var pets = Driver.getDriver().findElements(PETS_TABLES);
        if (pets.isEmpty()) return currentPets;

        var petsList = new ArrayList<PetDto>();

        for (var pet : pets) {
            var currentPet = new PetDto();

            var petDetails = pet.findElements(By.cssSelector("dl.dl-horizontal dd"));

            currentPet.setName(petDetails.get(0).getText());
            currentPet.setBirthDate(petDetails.get(1).getText());
            currentPet.setType(petDetails.get(2).getText());

            var buttons = pet.findElements(By.cssSelector("table.table-condensed tbody td > a"));

            currentPet.setEditButton(buttons.get(0));
            currentPet.setAddVisitButton(buttons.get(1));

            var visitsList = new ArrayList<VisitDto>();

            var petVisits = pet.findElements(By.cssSelector("table.table-condensed tbody tr"));

            if (petVisits.size() > 1) {
                for (int i = 0; i < petVisits.size() - 1; i++) {
                    var currentVisit = new VisitDto();
                    var petVisit = petVisits.get(i);

                    currentVisit.setDate(petVisit.findElement(By.cssSelector("td[headers='visitDate']")).getText());
                    currentVisit.setDescription(petVisit.findElement(By.cssSelector("td[headers='visitDescription']")).getText());

                    if (!currentVisit.isEmpty()) visitsList.add(currentVisit);
                }
            }

            if (!visitsList.isEmpty()) currentPet.setVisits(visitsList);
            if (!currentPet.isEmpty()) petsList.add(currentPet);
        }

        return currentPets = petsList;
    }

    public OwnerFromPageDto getOwnerFromPage() {
        var owner = new OwnerFromPageDto();

        owner.setName(this.getOwnerName());
        owner.setAddress(this.getOwnerAddress());
        owner.setCity(this.getOwnerCity());
        owner.setTelephone(this.getOwnerTelephone());

        owner.setPets(this.getAllPets());

        return owner;
    }

    public OwnerPage validateOwnerIs(OwnerCreateDto expected) {
        var expectedOwner = new OwnerFromPageDto().convertToOwnerFromPage(expected);
        var actualOwner = this.getOwnerFromPage();

        var message = "Asserting owner name is [%s]";
        assertions.assertWithMessage(message, expectedOwner.getName())
                .assertThat(expectedOwner.getName())
                .isEqualTo(actualOwner.getName());

        message = "Asserting owner city is [%s]";
        assertions.assertWithMessage(message, expectedOwner.getCity())
                .assertThat(expectedOwner.getCity())
                .isEqualTo(actualOwner.getCity());

        message = "Asserting owner address is [%s]";
        assertions.assertWithMessage(message, expectedOwner.getAddress())
                .assertThat(expectedOwner.getAddress())
                .isEqualTo(actualOwner.getAddress());

        message = "Asserting owner telephone is [%s]";
        assertions.assertWithMessage(message, expectedOwner.getTelephone())
                .assertThat(expectedOwner.getTelephone())
                .isEqualTo(actualOwner.getTelephone());

        assertions.assertAll();

        return this;
    }

    public void validateOwnerHasPets(PetDto... expectedPets) {
        var actualPets = getAllPets();

        var message = "Validating that Owner has pets %s";
        assertions.assertWithMessage(message, Arrays.toString(expectedPets))
                .assertThat(actualPets)
                .containsExactlyElementsIn(expectedPets);

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);
    }

    public EditPetPage editPet(@NonNull final String name) {
        getAllPets().stream()
                .filter(pet -> pet.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError(String.format("Pet with name [%s] was not found", name)))
                .getEditButton()
                .click();

        return new EditPetPage();
    }

    public AddVisitPage addPetVisit(String name) {
        getAllPets().stream()
                .filter(pet -> pet.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError(String.format("Pet with name [%s] was not found", name)))
                .getAddVisitButton()
                .click();

        return new AddVisitPage();
    }

    public OwnerPage validatePetVisitHasBeenCreated(String petName, VisitDto expectedVisit) {
        var errorMessage = String.format("Pet with name [%s] was not found", petName);

        Sleep.forSeconds(2);

        var actualVisits = getAllPets().stream()
                .filter(pet -> pet.getName().equals(petName))
                .findFirst()
                .orElseThrow(() -> new AssertionError(errorMessage))
                .getVisits();

        var message = "Validating that Pet has Visit %s";
        assertions.assertWithMessage(message, expectedVisit).assertThat(actualVisits)
                .contains(expectedVisit);

        assertions.assertAll();

        var screenshotBase64 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
        Log.infoWithScreenshotByBase64String("Screenshot Attached", screenshotBase64);

        return this;
    }
}
