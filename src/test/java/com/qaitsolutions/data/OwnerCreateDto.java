package com.qaitsolutions.data;

import com.github.javafaker.Faker;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OwnerCreateDto {

    private static final Faker FAKER = Faker.instance();

    @Builder.Default
    private String firstName = FAKER.name().firstName();

    @Builder.Default
    private String lastName = FAKER.name().lastName();

    @Builder.Default
    private String address = FAKER.address().fullAddress();

    @Builder.Default
    private String city = FAKER.address().city();

    @Builder.Default
    private String telephone = FAKER.numerify("07########");
}
