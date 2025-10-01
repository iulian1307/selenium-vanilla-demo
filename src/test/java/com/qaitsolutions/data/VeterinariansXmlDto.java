package com.qaitsolutions.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Vets")
public class VeterinariansXmlDto {

    @JacksonXmlProperty(localName = "vet")
    private List<VeterinarianDto> vets;
}
