package com.agility.usermanagement.controllers;

import com.agility.usermanagement.dto.UserRegistry;
import com.agility.usermanagement.exceptions.BadAccountCredentialException;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.securities.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.agility.usermanagement.exceptions.CustomError.BAD_CREDENTIALS;

@RestController
@RequestMapping("/v1/auths")
public class AuthController extends BaseController {
    /**
     * Sign in app
     *
     * @param credential Authentication request
     * @return Response with token attached in header
     * @throws BadAccountCredentialException if invalid credential or returned user null id
     */
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest credential) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credential.getUsername(),
                credential.getPassword()));

            User user = userRepository.findByUsername(credential.getUsername())
                .orElseThrow(null);

            String token = jwtTokenService.createToken(user);

            if (token == null) {
                throw new BadAccountCredentialException(BAD_CREDENTIALS);
            }

            return ResponseEntity.ok().header(securityConfig.getHeaderString(), token).body(null);
        } catch (BadCredentialsException e) {
            throw new BadAccountCredentialException(BAD_CREDENTIALS);
        }
    }
}
