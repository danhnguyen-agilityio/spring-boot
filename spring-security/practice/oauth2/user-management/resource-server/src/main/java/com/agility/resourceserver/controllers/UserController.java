package com.agility.resourceserver.controllers;

import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.mappers.UserProfileMapper;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;

import static com.agility.resourceserver.exceptions.CustomError.USER_NOT_FOUND;

@RestController
public class UserController {

    private UserProfileRepository userProfileRepository;
    private UserProfileMapper userProfileMapper;
    private RestTemplate restTemplate;

    public UserController(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
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
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
            uri,
            HttpMethod.DELETE,
            entity,
            Boolean.class);
        return responseEntity.getBody();
    }
}
