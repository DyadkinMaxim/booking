package com.example.bookings.converter;

import com.example.bookings.dto.BookingDTO;
import com.example.bookings.exception.UnprocessableException;
import com.example.bookings.model.Booking;
import com.example.bookings.service.FilmService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class BookingConverterImpl implements BookingConverter {

    private final FilmService filmService;

    public BookingConverterImpl(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public BookingDTO toDTO(Booking booking) {
        var bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setSeatNumber(booking.getSeatNumber());
        bookingDTO.setBookingDate(booking.getBookingDate().toString());
        bookingDTO.setFilmId(booking.getFilm().getFilmId());
        return bookingDTO;
    }

    @Override
    public Booking toEntity(long filmId, String isoDate, int seatNumber) {
        LocalDate bookingDate;
        try{
            bookingDate = LocalDate.parse(isoDate);
        } catch(DateTimeParseException ex) {
            throw new UnprocessableException("Invalid time format: " + isoDate);
        }
        var booking = new Booking();
        booking.setFilm(filmService.findById(filmId));
        booking.setBookingDate(bookingDate);
        booking.setSeatNumber(seatNumber);
        return booking;
    }
}
