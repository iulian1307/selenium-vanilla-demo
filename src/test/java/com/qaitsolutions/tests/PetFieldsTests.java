package com.qaitsolutions.tests;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.data.PetDto;
import com.qaitsolutions.page_objects.OwnerPage;
import com.qaitsolutions.page_objects.OwnersListPage;
import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDate;

@Listeners(PFrameListener.class)
public class PetFieldsTests extends BaseTest {

    private final PetClinicPage homepage = new PetClinicPage();
    private OwnerCreateDto owner = new OwnerCreateDto();
    private PetDto pet;

    @BeforeClass
    public void beforeClass() {
        var ownerFromPage = homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .clickOnFindOwnerButton()
                .selectRandomOwnerWithPet()
                .getOwnerFromPage();

        owner = owner.convertToOwnerCreate(ownerFromPage);
        pet = ownerFromPage.getPets().get(0);
    }

    @Test(description = "Create Pet fields can't be empty")
    public void createPetFieldsCanNotBeEmpty() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .addNewPet()
                .validateOnAddPetPage()
                .submitPetFail()
                .checkNameFieldIsRequired()
                .checkBirthDateFieldIsRequired()
                .checkTypeFieldIsRequired()
                .validateAll();
    }

    @Test(description = "Name field is mandatory")
    public void nameFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .addNewPet()
                .validateOnAddPetPage()
                .selectBirthDate(LocalDate.parse(pet.getBirthDate()))
                .selectPetType(pet.getType())
                .submitPetFail()
                .checkNameFieldIsRequired()
                .validateAll();
    }

    @Test(description = "Birth Date field is mandatory")
    public void birthDateFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .addNewPet()
                .validateOnAddPetPage()
                .insertName(pet.getName())
                .selectPetType(pet.getType())
                .submitPetFail()
                .checkBirthDateFieldIsRequired()
                .validateAll();
    }

    @Test(description = "Type field is mandatory")
    public void typeFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .addNewPet()
                .validateOnAddPetPage()
                .insertName(pet.getName())
                .selectBirthDate(LocalDate.parse(pet.getBirthDate()))
                .submitPetFail()
                .checkTypeFieldIsRequired()
                .validateAll();
    }

    @Test(description = "Add visits fields are mandatory")
    public void addVisitsFieldsAreMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .addPetVisit(pet.getName())
                .submitVisitFail()
                .checkDescriptionEmptyFieldError()
                .validateAll();
    }
}
