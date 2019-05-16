package com.pack.example.clientresttemplate.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationCodeTokenService {

    @Autowired
    private AuthorizationCodeConfiguration configuration;

    public String getAuthorizationEndpoint() {
        String endpoint = "http://localhost:8080/oauth/authorize";
        Map<String, String> authParameters = new HashMap<>();
        authParameters.put("client_id", "clientapp");
        authParameters.put("response_type", "code");
        authParameters.put("redirect_uri",
            getEncodeUrl("http://localhost:9000/callback"));
        return buildUrl(endpoint, authParameters);
    }

    private String buildUrl(String endpoint, Map<String, String> parameters) {
        List<String> paramList = new ArrayList<>(parameters.size());
        parameters.forEach((name, value) -> {
            paramList.add(name + "=" + value);
        });
        return endpoint + "?" + paramList.stream()
            .reduce((a, b) -> a + "&" + b ).get();
    }

    private String getEncodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    public OAuth2Token getToken (String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        String authBase64 = configuration.encodeCredentials("clientapp", "123456");
        RequestEntity requestEntity = new RequestEntity(
            configuration.getBody(authorizationCode),
            configuration.getHeaders(authBase64),
            HttpMethod.POST,
            URI.create("http://localhost:8080/oauth/token"));

        ResponseEntity<OAuth2Token> responseEntity = restTemplate.exchange(
            requestEntity, OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        throw new RuntimeException("error trying to retrieve access token");
    }
}
