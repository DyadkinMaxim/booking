package com.example.bookings.controller;

import com.example.bookings.dto.BookingDTO;
import com.example.bookings.exception.AlreadyExistException;
import com.example.bookings.exception.NotFoundException;
import com.example.bookings.exception.UnprocessableException;
import com.example.bookings.converter.BookingConverter;
import com.example.bookings.service.BookingService;
import com.example.bookings.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BookingControllerImpl implements BookingController {

    private final BookingService bookingService;
    private final FilmService filmService;
    private final BookingConverter bookingConverter;

    public BookingControllerImpl(BookingService bookingService,
                                 FilmService filmService,
                                 BookingConverter bookingConverter) {
        this.bookingService = bookingService;
        this.filmService = filmService;
        this.bookingConverter = bookingConverter;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listBookings/{date}")
    @Override
    public List<BookingDTO> findByBookingDate(@PathVariable(value = "date") String date) {
        return bookingService.findByBookingDate(LocalDate.parse(date)).stream().map(
                bookingConverter::toDTO)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getBooking/{bookingId}")
    @Override
    public BookingDTO findById(@PathVariable(value = "bookingId") long bookingId) {
        return bookingConverter.toDTO(bookingService.findById(bookingId));

    }

    @ResponseStatus(HttpStatus.OK) // usually preferred HTTP 201 with created entity
    @PostMapping("/createBooking/{filmId}/{date}/{seat}")
    @Override
    public void saveBooking(
            @PathVariable(value = "filmId") long filmId,
            @PathVariable(value = "date") String date,
            @PathVariable(value = "seat") int seatNumber) {
        log.debug("Started creating new booking with filmId {} on {} at seat: {}",
                filmId, date, seatNumber);
        bookingConverter.toDTO(
                bookingService.save(bookingConverter.toEntity(filmId, date, seatNumber)));
    }

    @ResponseStatus(HttpStatus.OK) // usually preferred HTTP 204
    @DeleteMapping("/cancelBooking/{bookingId}")
    @Override
    public void deleteById(@PathVariable(value = "bookingId") long bookingId) {
        log.debug("Started cancellation booking with id {}", bookingId);
        bookingService.deleteById(bookingId);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> alreadyExistException(AlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<?> unprocessableException(UnprocessableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // For any other exceptions
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
