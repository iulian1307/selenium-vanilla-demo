package com.qaitsolutions.tests;

import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.page_objects.OwnerContainer;
import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(PFrameListener.class)
public class OwnerFieldsTests extends BaseTest {

    private final PetClinicPage homepage = new PetClinicPage();
    private final OwnerCreateDto owner = OwnerContainer.getOwner();

    @Test(description = "Create Owner fields can't be empty")
    public void createOwnerFieldsCanNotBeEmpty() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .submitOwnerFail()
                .checkFirstNameEmptyFieldError()
                .checkLastNameEmptyFieldError()
                .checkAddressEmptyFieldError()
                .checkCityEmptyFieldError()
                .checkTelephoneFieldError()
                .validateAll();
    }

    @Test(description = "First Name field is mandatory")
    public void firstNameFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone())
                .submitOwnerFail()
                .checkFirstNameEmptyFieldError()
                .validateAll();
    }

    @Test(description = "Last Name field is mandatory")
    public void lastNameFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .insertFirstName(owner.getFirstName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone())
                .submitOwnerFail()
                .checkLastNameEmptyFieldError()
                .validateAll();
    }

    @Test(description = "Address field is mandatory")
    public void addressFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertCity(owner.getCity())
                .insertTelephone(owner.getTelephone())
                .submitOwnerFail()
                .checkAddressEmptyFieldError()
                .validateAll();
    }

    @Test(description = "City field is mandatory")
    public void cityFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertTelephone(owner.getTelephone())
                .submitOwnerFail()
                .checkCityEmptyFieldError()
                .validateAll();
    }

    @Test(description = "Telephone field is mandatory")
    public void telephoneFieldIsMandatory() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .addOwnerButton()
                .assertOnAddOwnerPage()
                .insertFirstName(owner.getFirstName())
                .insertLastName(owner.getLastName())
                .insertAddress(owner.getAddress())
                .insertCity(owner.getCity())
                .submitOwnerFail()
                .checkTelephoneFieldError()
                .validateAll();
    }
}
