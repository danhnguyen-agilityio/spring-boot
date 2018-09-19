package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.exceptions.BadAccountCredentialException;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.securities.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.agility.shopping.cart.exceptions.CustomError.BAD_CREDENTIALS;

@RestController
@RequestMapping("v1/auths")
public class AuthController extends BaseController {
    /**
     * Signin app
     *
     * @param data Authentication request
     * @return Response with token attached in header
     * @throws BadAccountCredentialException if invalid username or password
     */
    @PostMapping
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                data.getPassword()));
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            String token = jwtTokenService.createToken(user);

            return ResponseEntity.ok().header(securityConfig.getHeaderString(),token).body(null);
        } catch (BadCredentialsException e) {
            throw new BadAccountCredentialException(BAD_CREDENTIALS);
        }
    }
}
