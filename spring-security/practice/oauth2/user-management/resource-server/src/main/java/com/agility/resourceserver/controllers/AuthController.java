package com.agility.resourceserver.controllers;

import com.agility.resourceserver.configs.SecurityProperties;
import com.agility.resourceserver.dto.AuthRequest;
import com.agility.resourceserver.dto.AuthResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * Endpoint used for Backend App
 */
@RestController
@RequestMapping("/auths")
public class AuthController {
    private SecurityProperties securityProperties;

    public AuthController(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        RestTemplate testRestTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromHttpUrl(securityProperties.getTokenEndpoint())
            .queryParam("grant_type", "password")
            .queryParam("username", authRequest.getUsername())
            .queryParam("password", authRequest.getPassword())
            .queryParam("scope", "read")
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setBasicAuth(securityProperties.getClientId(), securityProperties.getClientSecret());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AuthResponse> responseEntity = testRestTemplate.exchange(
            uri,
            HttpMethod.POST,
            entity,
            AuthResponse.class);

        return responseEntity;
    }

    @PostMapping("/registry")
    public ResponseEntity registry(@Valid @RequestBody )
}
