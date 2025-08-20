package com.qaitsolutions.data;

import com.github.javafaker.Faker;
import com.qaitsolutions.selenium.wrapper.utils.RandomDateGenerator;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class VisitDto {

    private static final Faker FAKER = Faker.instance();

    @Builder.Default
    private String date = RandomDateGenerator
            .generateRandomDate(
                    LocalDate.now().minusYears(2),
                    LocalDate.now().minusDays(1)
            ).toString();

    @Builder.Default
    private String description = FAKER.lorem().paragraph(1);

    public boolean isEmpty() {
        return date == null && description == null;
    }
}
