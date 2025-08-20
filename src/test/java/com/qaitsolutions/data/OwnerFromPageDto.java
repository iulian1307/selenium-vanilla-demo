package com.qaitsolutions.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class OwnerFromPageDto {
    private String name;
    private String address;
    private String city;
    private String telephone;
    private List<PetDto> pets;

    public OwnerFromPageDto convertToOwnerFromPage(OwnerCreateDto owner) {
        name = owner.getFirstName() + " " + owner.getLastName();
        address = owner.getAddress();
        city = owner.getCity();
        telephone = owner.getTelephone();

        return this;
    }
}
