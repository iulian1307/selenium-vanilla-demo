package com.qaitsolutions.page_objects;

import com.github.javafaker.Faker;
import com.qaitsolutions.data.OwnerCreateDto;
import com.qaitsolutions.data.PetDto;
import com.qaitsolutions.data.PetType;
import com.qaitsolutions.data.VisitDto;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OwnerContainer {

    private static final Faker FAKER = Faker.instance();

    @Getter @Setter
    private static OwnerCreateDto owner;

    @Getter
    private static List<PetDto> pets;

    static {
        owner = OwnerCreateDto.builder().build();

        var pet1 = new PetDto();
        pet1.setName(FAKER.name().firstName());
        pet1.setType(PetType.getRandom().getIdentifier());
        pet1.setBirthDate(LocalDate.now().minusMonths(1).toString());
        pet1.setVisits(List.of(VisitDto.builder().build(), VisitDto.builder().build()));

        var pet2 = new PetDto();
        pet2.setName(FAKER.name().firstName());
        pet2.setType(PetType.getRandom().getIdentifier());
        pet2.setBirthDate(LocalDate.now().minusMonths(6).toString());

        pets = new ArrayList<>();
        pets.add(pet1);
        pets.add(pet2);
    }

    public static PetDto getPet(@NonNull final String name) {
        return pets.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(new PetDto());
    }
}
