package com.qaitsolutions.data;

import com.github.javafaker.Faker;
import lombok.*;
import org.openqa.selenium.WebElement;

import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class PetDto {

    private static final Faker FAKER = Faker.instance();

    private String name;
    private String birthDate;
    private String type;
    private WebElement editButton;
    private WebElement addVisitButton;
    private List<VisitDto> visits;

    public boolean isEmpty() {
        return name == null
                && birthDate == null
                && type == null
                && visits == null;
    }
}
