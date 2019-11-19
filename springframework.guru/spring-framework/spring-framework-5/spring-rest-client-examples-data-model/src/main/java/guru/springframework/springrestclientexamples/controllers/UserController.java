package guru.springframework.springrestclientexamples.controllers;

import guru.springframework.springrestclientexamples.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Controller
public class UserController {

    private ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {

//        MultiValueMap<String, String> map = serverWebExchange.getFormData().block();
//
//        Integer limit = new Integer(map.get("limit").get(0));
//
//        if (limit == null || limit == 0) {
//            limit = 10;
//        }
//
//        model.addAttribute("users", apiService.getUsers(limit));
//
//        return "userlist";

        model.addAttribute("users",
                apiService.getUsers(serverWebExchange
                        .getFormData()
                        .map(data -> new Integer(data.getFirst("limit")))));

        return "userlist";
    }
}
