package io.github.dft.mailchimp;

import io.github.dft.mailchimp.model.Interests.InterestsWrapper;
import io.github.dft.mailchimp.model.tokens.Credentials;

import java.net.URI;
import java.net.http.HttpRequest;

public class InterestsAPI extends MailchimpSdk {

    public InterestsAPI(Credentials credentials) {
        super(credentials);
    }

    public InterestsWrapper getInterests(String listId, String interestCategoryId) {

        URI uri = baseUrl("/3.0/lists/" + listId + "/interest-categories/" + interestCategoryId + "/interests");
        HttpRequest request = get(uri);
        return getRequestWrapped(request, InterestsWrapper.class);
    }
}
