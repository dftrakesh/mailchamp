package io.github.dft.mailchimp.model.Interests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Interest {

    public String categoryId;
    public String listId;
    public String id;
    public String name;
    public String subscriberCount;
    public int displayOrder;
}

