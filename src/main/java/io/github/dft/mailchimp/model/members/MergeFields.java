package io.github.dft.mailchimp.model.members;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
public class MergeFields {

    private String fName;
    private String lName;
    private String phone;
    private String role;
    private String gradyear;
    private String grade;
    private String prefname;
}