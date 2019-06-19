package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dtos.AppUserResponse;
import com.agility.usermanagement.dtos.UserCreatedRequest;
import com.agility.usermanagement.mappers.UserMapper;
import com.agility.usermanagement.models.AppUser;
import com.agility.usermanagement.services.UserService;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;
import com.okta.sdk.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
@CrossOrigin(origins = "*")
public class PublicController {

    private Client client;
    private UserMapper userMapper;
    private UserService userService;
    private AccessTokenVerifier jwtAccessTokenVerifier;
    private AccessTokenVerifier jwtIdTokenVerifier;

    public PublicController(Client client, UserMapper userMapper, UserService userService) {
        this.client = client;
        this.userMapper = userMapper;
        this.userService = userService;

        jwtAccessTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer("https://dev-343362.okta.com/oauth2/default")
            .setAudience("api://default")
            .setConnectionTimeout(Duration.ofDays(1))
            .setReadTimeout(Duration.ofDays(1))
            .build();

        jwtIdTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
            .setIssuer("https://dev-343362.okta.com/oauth2/default")
            .setAudience("0oan4mt8yDscBwAOF356")
            .setConnectionTimeout(Duration.ofDays(1))
            .setReadTimeout(Duration.ofDays(1))
            .build();

    }

    @PostMapping("/signup")
    public AppUserResponse signup(@Valid @RequestBody UserCreatedRequest request) {
        log.debug("POST /api/v1/public/signup body={}");

        AppUser appUser = userMapper.toAppUser(request);

        return userService.createUser(appUser);
    }

    /**
     * Validate token
     * @param token token need validate
     *
     * @return
     *  - Success Status with Claim data
     *  - Unauthorize Status if token not valid in these case:
     *      - Token expiration date
     *      - Valid token not before date
     *      - The token issuer matches the expected value passed into the above helper
     *      - The token audience matches the expected value passed into the above helper
     */
    @PostMapping("/validate-token")
    public ResponseEntity validateToken(@Valid @RequestParam(name = "token") String token, @RequestParam String type) {
        try {
            Jwt jwt;
            if ("id_token".equalsIgnoreCase(type)) {
                jwt = jwtIdTokenVerifier.decode(token);
            } else {
                jwt = jwtAccessTokenVerifier.decode(token);
            }
            return ResponseEntity.ok(jwt.getClaims());
        } catch (JwtVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }
}
