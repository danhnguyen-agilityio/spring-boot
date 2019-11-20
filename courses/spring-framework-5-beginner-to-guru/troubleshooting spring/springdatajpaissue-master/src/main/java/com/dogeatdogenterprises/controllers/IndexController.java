package com.dogeatdogenterprises.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
@Controller
public class IndexController {

    @RequestMapping({"/", ""})
    public String index() {
        return "index";
    }

    @RequestMapping({"/access_denied"})
    public String notAuth() {
        return "access_denied";
    }

    @RequestMapping({"login"})
    public String loginForm() {
        return "login";
    }
}
