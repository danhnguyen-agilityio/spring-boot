package guru.springframework.json.mb2gspringbootmm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import guru.springframework.json.model.ShippingAddress;

@RestController
public class ExampleController {

    @GetMapping
    public ShippingAddress index() {
        return new ShippingAddress();
    }
}
