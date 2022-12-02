package com.example.bookings.controller;

import com.example.bookings.dto.BookingDTO;

import java.util.List;

public interface BookingController {
    List<BookingDTO> findByBookingDate(String date);

    BookingDTO findById(long id);

    void  saveBooking(long filmId, String date, int seatNumber);

    void deleteById(long id);
}
