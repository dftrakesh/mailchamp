package io.github.dft.mailchimp.model.members;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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