package com.agility.authorizationserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class TokenController {

    @Autowired
    private DefaultTokenServices tokenServices;

    @RequestMapping(method = RequestMethod.DELETE, path = "/revoke")
    @ResponseStatus(HttpStatus.OK)
    public boolean revokeToken(@RequestParam("token") String userToken) {
        // final String userToken = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        return tokenServices.revokeToken(userToken);
    }
}
