package com.qaitsolutions.tests;

import com.qaitsolutions.data.VeterinarianDto;
import com.qaitsolutions.page_objects.PetClinicPage;
import com.qaitsolutions.pframe.core.testng.PFrameListener;
import com.qaitsolutions.selenium.wrapper.utils.JsonUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(PFrameListener.class)
public class VeterinariansTests extends BaseTest {

    private static final String VETERINARIANS_PATH = "src/test/resources/test-data/veterinarians.json";

    private final PetClinicPage homepage = new PetClinicPage();

    private List<VeterinarianDto> veterinarianDto;

    @BeforeClass
    public void beforeClass() {
        veterinarianDto = JsonUtils.deserializeFromPath(VETERINARIANS_PATH, VeterinarianDto.class);
    }

    @Test(description = "Validate veterinarians table is correct")
    public void veterinariansTableIsCorrect() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openVeterinariansPage()
                .validateOnVeterinariansPage()
                .validateTableHasValues(veterinarianDto);
    }

    @Test(description = "Validate veterinarians JSON is correct")
    public void veterinariansJsonIsCorrect() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openVeterinariansPage()
                .validateOnVeterinariansPage()
                .openJsonPage()
                .validateOnVeterinariansJsonPage()
                .validateJsonHasValues(veterinarianDto);
    }

    @Test(description = "Validate veterinarians XML is correct")
    public void veterinariansXmlIsCorrect() {
        homepage.openWebpage()
                .validateOnHomepage()
                .openVeterinariansPage()
                .validateOnVeterinariansPage()
                .openXmlPage()
                .validateOnVeterinariansXmlPage()
                .validateXmlHasValues(veterinarianDto);
    }
}
