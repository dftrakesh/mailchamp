package io.github.dft.mailchimp.model.tokens;

import lombok.Data;

@Data
public class Credentials {

    private String apiKey;
    private String accountId;
}