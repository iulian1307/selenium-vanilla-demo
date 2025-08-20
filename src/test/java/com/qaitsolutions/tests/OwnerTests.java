package com.qaitsolutions.tests;

import com.github.javafaker.Faker;
import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.page_objects.OwnerContainer;
import com.qaitsolutions.page_objects.OwnerPage;
import com.qaitsolutions.page_objects.OwnersListPage;
import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(PFrameListener.class)
public class OwnerTests extends BaseTest {

    private final PetClinicPage homepage = new PetClinicPage();
    private final OwnerCreateDto owner = OwnerContainer.getOwner();
    private final OwnerCreateDto editedOwner = new OwnerCreateDto();

    @BeforeClass
    public void beforeClass() {
        editedOwner.setFirstName(owner.getFirstName() + "_edit");
        editedOwner.setLastName(owner.getLastName() + "_edit");
        editedOwner.setAddress(owner.getAddress() + "_edit");
        editedOwner.setTelephone(Faker.instance().numerify("07########"));
        editedOwner.setCity(owner.getCity() + "_edit");
    }

    @Test(description = "Create Owner")
    public void createOwner() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .addOwner(owner)
                .validateOnOwnerPage()
                .validateOwnerIs(owner);
    }

    @Test(description = "Find created Owner", dependsOnMethods = "createOwner")
    public void findCreatedOwner() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnerPage.isOnPage()
                ? new OwnerPage()
                : new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner);

        ownerPage.validateOwnerIs(owner);
    }

    @Test(description = "Edit created Owner", dependsOnMethods = "findCreatedOwner")
    public void editCreatedOwner() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .findOwnerByLastName(owner.getLastName());

        var ownerPage = OwnersListPage.isOnPage()
                ? new OwnersListPage().validateOnOwnersListPage().selectUserFromTable(owner)
                : new OwnerPage();

        ownerPage.validateOnOwnerPage()
                .editOwner()
                .validateOnEditOwnerPage()
                .updateOwner(editedOwner)
                .validateOwnerIs(editedOwner);

        OwnerContainer.setOwner(editedOwner);
    }
}
