package io.github.dft.mailchimp;

import io.github.dft.mailchimp.model.members.MemberWrapper;
import io.github.dft.mailchimp.model.tokens.Credentials;
import io.github.dft.mailchimp.model.members.MemberRequest;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;

public class MemberAPI extends MailchimpSdk {

    public MemberAPI(Credentials credentials) {
        super(credentials);
    }

    public MemberWrapper createMember(String listId, MemberRequest body) {

        URI uri = baseUrl("/3.0/lists/" + listId + "/members");
        HttpRequest request = postWithObject(uri, body);
        return getRequestWrapped(request, MemberWrapper.class);
    }

    public MemberWrapper updateMember(String listId, String contactId, MemberRequest body) {

        URI uri = baseUrl("/3.0/lists/" + listId + "/members/" + contactId);
        HttpRequest request = patchWithObject(uri, body);
        return getRequestWrapped(request, MemberWrapper.class);
    }

    @SneakyThrows
    public void archiveMember(String listId, String contactId) {

        URI uri = baseUrl("/3.0/lists/" + listId + "/members/" + contactId);
        HttpRequest request = delete(uri);
        getRequestWrapped(request, MemberWrapper.class);
    }
}
