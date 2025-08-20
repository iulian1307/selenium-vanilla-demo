package com.qaitsolutions.tests;

import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(PFrameListener.class)
public class NavigationTests extends BaseTest {

    @Test(description = "Navigate to Pages Validations")
    public void navigateToPages() {
        new PetClinicPage()
                .openWebpage()
                .validateOnHomepage()
                .openFindOwnersPage()
                .validateOnFindOwnersPage()
                .openVeterinariansPage()
                .validateOnVeterinariansPage()
                .openErrorPage()
                .validateOnErrorPage();
    }
}