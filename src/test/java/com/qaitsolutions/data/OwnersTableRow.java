package com.qaitsolutions.data;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;

import java.util.List;

@Getter @Setter
public class OwnersTableRow {
    private String name;
    private String address;
    private String city;
    private String telephone;
    private List<String> pets;
    private WebElement link;
}
