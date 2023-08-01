package io.github.dft.mailchimp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dft.mailchimp.model.tokens.Credentials;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static io.github.dft.mailchimp.constantcode.ConstantCodes.ACCEPT;
import static io.github.dft.mailchimp.constantcode.ConstantCodes.AUTHORIZATION;
import static io.github.dft.mailchimp.constantcode.ConstantCodes.BASE_ENDPOINT;
import static io.github.dft.mailchimp.constantcode.ConstantCodes.BASIC;
import static io.github.dft.mailchimp.constantcode.ConstantCodes.CONTENT_TYPE;
import static io.github.dft.mailchimp.constantcode.ConstantCodes.HTTPS;

public class MailchimpSdk {

    protected Credentials credentials;
    protected HttpClient client;
    public final ObjectMapper objectMapper;

    int MAX_ATTEMPTS = 50;
    int TIME_OUT_DURATION = 60000;

    @SneakyThrows
    public MailchimpSdk(Credentials credentials) {
        this.credentials = credentials;
        this.objectMapper = new ObjectMapper();
        client = HttpClient.newHttpClient();
    }

    @SneakyThrows
    protected HttpRequest get(URI uri) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .GET()
            .build();
    }

    @SneakyThrows
    protected HttpRequest patch(URI uri, String jsonBody) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected HttpRequest delete(URI uri) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .DELETE()
            .build();
    }

    @SneakyThrows
    protected HttpRequest post(URI uri, String jsonBody) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected HttpRequest patchWithObject(URI uri, Object object) {
        String jsonBody = objectMapper.writeValueAsString(object);

        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected HttpRequest postWithObject(URI uri, Object object) {
        String jsonBody = objectMapper.writeValueAsString(object);

        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BASIC + credentials.getApiKey())
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected URI addParameters(URI uri, HashMap<String, String> params) {

        if (params == null) return uri;
        String query = uri.getQuery();
        StringBuilder builder = new StringBuilder();
        if (query != null)
            builder.append(query);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String keyValueParam = entry.getKey() + "=" + entry.getValue();
            if (!builder.toString().isEmpty())
                builder.append("&");
            builder.append(keyValueParam);
        }
        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
    }

    @SneakyThrows
    protected URI baseUrl(String path) {
        return new URI(new StringBuilder().append(HTTPS)
            .append(credentials.getAccountId())
            .append(BASE_ENDPOINT)
            .append(path)
            .toString());
    }

    @SneakyThrows
    protected <T> T getRequestWrapped(HttpRequest request, Class<T> tClass) {

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenComposeAsync(response -> tryResend(client, request, HttpResponse.BodyHandlers.ofString(), response, 1))
            .thenApplyAsync(HttpResponse::body)
            .thenApplyAsync(responseBody -> {
                if (responseBody.isEmpty()) return null;
                return convertBody(responseBody, tClass);
            })
            .get();
    }

    @SneakyThrows
    public <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                            HttpRequest request,
                                                            HttpResponse.BodyHandler<T> handler,
                                                            HttpResponse<T> resp, int count) {
        if (resp.statusCode() == 429 && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }

        return CompletableFuture.completedFuture(resp);
    }

    @SneakyThrows
    private <T> T convertBody(String body, Class<T> tClass) {
        System.out.println("body = " + body);
        return objectMapper.readValue(body, tClass);
    }
}