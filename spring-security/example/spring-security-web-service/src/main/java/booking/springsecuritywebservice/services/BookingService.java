package booking.springsecuritywebservice.services;

import booking.springsecuritywebservice.models.Booking;
import booking.springsecuritywebservice.models.BookingRequest;
import booking.springsecuritywebservice.models.BookingResponse;
import booking.springsecuritywebservice.models.DateRange;

import java.util.List;

public interface BookingService {

    /**
     * Looks up the booking with the given identifier
     *
     * @param bookingId the booking identifier to look up
     * @return the booking with the given ID
     */
    Booking getBooking(long bookingId);

    /**
     * Answers all bookings for the given date range
     *
     * @param dateRange the date range to retrieve bookings for
     * @return the bookings in the given date range
     */
    List<Booking> getBookings(DateRange dateRange);


    /**
     * Process the given booking
     * @param request the booking request
     * @return the result of the request
     */
    BookingResponse book(BookingRequest request);
}
