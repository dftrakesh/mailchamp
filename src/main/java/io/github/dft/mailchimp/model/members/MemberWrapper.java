package io.github.dft.mailchimp.model.members;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.HashMap;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberWrapper {

    private String emailAddress;
    private String status;
    private MergeFields mergeFields;
    private HashMap<String, Boolean> interests;
    private String contactId;
    private String webId;
    private String title;
    private String detail;
}