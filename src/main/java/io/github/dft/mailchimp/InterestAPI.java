package io.github.dft.mailchimp;

import io.github.dft.mailchimp.model.Interests.InterestsWrapper;
import io.github.dft.mailchimp.model.tokens.Credentials;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;

public class InterestAPI extends MailchimpSdk {

    public InterestAPI(Credentials credentials) {
        super(credentials);
    }

    public InterestsWrapper getInterests(String listId, String interestCategoryId, HashMap<String, String> params) {

        URI uri = addParameters(baseUrl("/3.0/lists/" + listId + "/interest-categories/" + interestCategoryId + "/interests"), params);
        HttpRequest request = get(uri);
        return getRequestWrapped(request, InterestsWrapper.class);
    }
}
