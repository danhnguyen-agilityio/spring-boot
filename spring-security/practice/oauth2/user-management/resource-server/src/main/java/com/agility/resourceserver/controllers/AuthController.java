package com.agility.resourceserver.controllers;

import com.agility.resourceserver.configs.SecurityProperties;
import com.agility.resourceserver.dto.AuthRequest;
import com.agility.resourceserver.dto.AuthResponse;
import com.agility.resourceserver.dto.UserResponse;
import com.agility.resourceserver.exceptions.InternalServerException;
import com.agility.resourceserver.exceptions.ResourceAlreadyExistsException;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static com.agility.resourceserver.exceptions.CustomError.INTERNAL_SERVER_ERROR;
import static com.agility.resourceserver.exceptions.CustomError.USERNAME_ALREADY_EXISTS;

/**
 * Endpoint used for Backend App
 */
@RestController
@RequestMapping("/auths")
public class AuthController {
    private SecurityProperties securityProperties;
    private UserProfileRepository userProfileRepository;
    private RestTemplate restTemplate;

    public AuthController(SecurityProperties securityProperties, UserProfileRepository userProfileRepository) {
        this.securityProperties = securityProperties;
        this.userProfileRepository = userProfileRepository;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
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

        ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
            uri,
            HttpMethod.POST,
            entity,
            AuthResponse.class);

        return responseEntity;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AuthRequest authRequest) {
        UserProfile userProfile = userProfileRepository.findByUsername(authRequest.getUsername());

        // User already exists
        if (userProfile != null) {
            throw new ResourceAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setBasicAuth(securityProperties.getClientId(), securityProperties.getClientSecret());
        HttpEntity<?> entity = new HttpEntity<>(authRequest, headers);

        try {
            ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(
                "http://localhost:8080/auths/register",
                HttpMethod.POST,
                entity,
                UserResponse.class);

            UserResponse userResponse = responseEntity.getBody();

            userProfile = UserProfile.builder()
                .id(userResponse.getId())
                .username(userResponse.getUsername())
                .build();

            userProfileRepository.save(userProfile);

            return responseEntity;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new ResourceAlreadyExistsException(USERNAME_ALREADY_EXISTS);
            } else {
                throw new InternalServerException(INTERNAL_SERVER_ERROR);
            }
        }
    }
}
