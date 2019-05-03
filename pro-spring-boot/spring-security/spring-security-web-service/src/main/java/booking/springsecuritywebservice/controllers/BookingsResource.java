package booking.springsecuritywebservice.controllers;

import booking.springsecuritywebservice.models.BookingDTO;
import booking.springsecuritywebservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingsResource {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/{bookingId}", method = RequestMethod.GET)
    public BookingDTO getBooking(@PathVariable("bookingId") long bookingId) {
        return new BookingDTO(bookingService.getBooking(bookingId));
    }
}
