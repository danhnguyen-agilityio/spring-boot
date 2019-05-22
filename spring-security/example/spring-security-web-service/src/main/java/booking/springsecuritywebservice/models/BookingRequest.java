package booking.springsecuritywebservice.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookingRequest {

    @Min(1)
    private final long roomId;

    @NotNull
    private final DateRange dateRange;

    @Size(min = 1, max = 128)
    private final String customerName;

    @NotNull
    private CreditCardDetails creditCardDetails;

    public BookingRequest(@Min(1) long roomId, @NotNull DateRange dateRange, @Size(min = 1, max = 128) String customerName) {
        this.roomId = roomId;
        this.dateRange = dateRange;
        this.customerName = customerName;
    }
}
