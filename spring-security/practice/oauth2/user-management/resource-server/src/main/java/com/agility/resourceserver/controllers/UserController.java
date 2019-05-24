package com.agility.resourceserver.controllers;

import com.agility.resourceserver.configs.SecurityProperties;
import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.exceptions.InternalServerException;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.mappers.UserProfileMapper;
import com.agility.resourceserver.models.Role;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.agility.resourceserver.exceptions.CustomError.INTERNAL_SERVER_ERROR;
import static com.agility.resourceserver.exceptions.CustomError.USER_NOT_FOUND;

@RestController
public class UserController {

    private UserProfileRepository userProfileRepository;
    private UserProfileMapper userProfileMapper;
    private SecurityProperties securityProperties;
    private RestTemplate restTemplate;

    public UserController(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, SecurityProperties securityProperties) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.securityProperties = securityProperties;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get self user info
     *
     * @return user data
     */
    @GetMapping("/me")
    public UserProfileResponse currentUser(Principal principal) {
        String username = principal.getName();
        UserProfile userProfile = userProfileRepository.findByUsername(username);

        if (userProfile == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        UserProfileResponse userProfileResponse = userProfileMapper.toUserProfileResponse(userProfile);
        return userProfileResponse;
    }

    @PostMapping("/logout-app")
    public boolean logout(Authentication authentication) {

        final String userToken = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();

        String uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/oauth/revoke")
            .queryParam("token", userToken)
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(securityProperties.getClientId(), securityProperties.getClientSecret());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
            uri,
            HttpMethod.DELETE,
            entity,
            Boolean.class);
        return responseEntity.getBody();
    }

    /**
     * Delete user (Only manager user and admin user have permission for feature)
     */
    @DeleteMapping("/users/{id}")
    @Secured({Role.MANAGER, Role.ADMIN})
    public void delete(@PathVariable Long id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElse(null);

        if (userProfile == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        try {

            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("id", id.toString());

            String uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/users/{id}")
                .buildAndExpand(uriParams)
                .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(securityProperties.getClientId(), securityProperties.getClientSecret());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                entity,
                Object.class);

            userProfileRepository.save(userProfile);

        } catch (HttpClientErrorException.NotFound e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException(USER_NOT_FOUND);
            } else {
                throw new InternalServerException(INTERNAL_SERVER_ERROR);
            }
        }
    }

}
