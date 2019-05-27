package booking.springsecuritywebservice.controllers;

import booking.springsecuritywebservice.models.ApiResponse;
import booking.springsecuritywebservice.models.BookingDTO;
import booking.springsecuritywebservice.models.BookingRequest;
import booking.springsecuritywebservice.models.BookingResponse;
import booking.springsecuritywebservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingsResource {

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{bookingId}", method = RequestMethod.GET)
    public BookingDTO getBooking(@PathVariable("bookingId") long bookingId) {
        return new BookingDTO(bookingService.getBooking(bookingId));
    }

    public ApiResponse book(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.book(request);
        return new ApiResponse(HttpStatus.OK, response);
    }
}
