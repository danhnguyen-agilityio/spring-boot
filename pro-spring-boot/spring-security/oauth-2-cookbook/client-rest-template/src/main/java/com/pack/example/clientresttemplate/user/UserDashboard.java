package com.pack.example.clientresttemplate.user;

import com.pack.example.clientresttemplate.oauth.AuthorizationCodeTokenService;
import com.pack.example.clientresttemplate.oauth.OAuth2Token;
import com.pack.example.clientresttemplate.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Controller
public class UserDashboard {

    @Autowired
    private AuthorizationCodeTokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/callback")
    public ModelAndView callback(String code, String state) {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();
        OAuth2Token token = tokenService.getToken(code);
        clientUser.setAccessToken(token.getAccessToken());
        Calendar tokenValidity = Calendar.getInstance();
        tokenValidity.setTime(new Date(Long.parseLong(token.getExpiresIn())));
        clientUser.setAccessTokenValidity(tokenValidity);
        clientUser.setRefreshToken(token.getRefreshToken());
        userRepository.save(clientUser);
        return new ModelAndView("forward:/dashboard");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        ClientUser clientUser = userDetails.getClientUser();
        if (clientUser.getAccessToken() == null) {
            String authEndpoint = tokenService.getAuthorizationEndpoint();
            return new ModelAndView("redirect:/" + authEndpoint);
        }
        clientUser.setEntries(Arrays.asList(
            new Entry("entry 1"),
            new Entry("entry 2")
        ));
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("user", clientUser);
        tryToGetUserProfile(mv, clientUser.getAccessToken());
        return mv;
    }

    private void tryToGetUserProfile(ModelAndView mv, String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:8080/api/profile";
        try {
            RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(endpoint));
            ResponseEntity<UserProfile> userProfile = restTemplate.exchange(
                request, UserProfile.class);
            if (userProfile.getStatusCode().is2xxSuccessful()) {
                mv.addObject("profile", userProfile.getBody());
            } else {
                throw new RuntimeException("it as not possible to retrieve user");
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }
}
