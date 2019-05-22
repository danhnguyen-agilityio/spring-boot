package booking.springsecuritywebservice.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreditCardDetails {

    @NotNull
    private String cardOwner;

    private String cardNumber;

    @Pattern(regexp = "[0-9]{2}/[0-9]{2}")
    private String expiration;

    @Pattern(regexp = "[0-9]{3,4}")
    private String cvv;
}
