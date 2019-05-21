package com.agility.oauth2example2.resources;

import com.agility.oauth2example2.model.Welcome;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = {"/api/hello"},
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Validated
public class Hello {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Welcome greetings(@RequestParam("name") String name) {
        return new Welcome(name);
    }
}
