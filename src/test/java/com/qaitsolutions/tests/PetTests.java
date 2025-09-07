package com.qaitsolutions.tests;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.data.PetDto;
import com.qaitsolutions.data.PetType;
import com.qaitsolutions.data.VisitDto;
import com.qaitsolutions.page_objects.OwnerContainer;
import com.qaitsolutions.page_objects.OwnerPage;
import com.qaitsolutions.page_objects.OwnersListPage;
import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDate;

@Listeners(PFrameListener.class)
public class PetTests extends BaseTest {

    private final PetClinicPage homepage = new PetClinicPage();
    private final OwnerCreateDto owner = OwnerContainer.getOwner();

    private PetDto pet1 = OwnerContainer.getPets().get(0);

    private final PetDto pet2 = OwnerContainer.getPets().get(1);
    private final PetDto editedPet = new PetDto();

    private final VisitDto pet1Visit1 = pet1.getVisits().get(0);
    private final VisitDto pet1Visit2 = pet1.getVisits().get(1);

    @BeforeClass
    public void beforeClass() {
        editedPet.setName(pet1.getName() + "_edit");
        editedPet.setType(PetType.getRandom().getIdentifier());
        editedPet.setBirthDate(LocalDate.parse(pet1.getBirthDate()).minusMonths(1).toString());
    }

    @Test(description = "Create Pet")
    public void createPet() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnerPage.isOnPage()
                ? new OwnerPage()
                : new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner);

        ownerPage.addNewPet()
                .validateOnAddPetPage()
                .addPet(pet1)
                .validateOnOwnerPage()
                .validateOwnerHasPets(pet1);
    }

    @Test(description = "Edit created Pet", dependsOnMethods = "createPet")
    public void editCreatedPet() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.editPet(pet1.getName())
                .validateOnEditPetPage()
                .updatePetDetails(editedPet)
                .validateOnOwnerPage()
                .validateOwnerHasPets(editedPet);

        pet1 = editedPet;
    }

    @Test(description = "Create additional Pet", dependsOnMethods = "createPet")
    public void createAdditionalPet() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnerPage.isOnPage()
                ? new OwnerPage()
                : new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner);

        ownerPage.addNewPet()
                .validateOnAddPetPage()
                .addPet(pet2)
                .validateOnOwnerPage()
                .validateOwnerHasPets(pet1, pet2);
    }

    @Test(description = "Add pet visits", dependsOnMethods = "createPet")
    public void addPetVisits() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.addPetVisit(pet1.getName())
                .validateOnAddVisitPage()
                .addVisit(pet1Visit1)
                .validatePetVisitHasBeenCreated(pet1.getName(), pet1Visit1)
                .addPetVisit(pet1.getName())
                .validateOnAddVisitPage()
                .validatePreviousVisitIsCorrect(pet1Visit1)
                .addVisit(pet1Visit2)
                .validatePetVisitHasBeenCreated(pet1.getName(), pet1Visit2);
    }
}
