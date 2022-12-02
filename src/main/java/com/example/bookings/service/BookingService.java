package com.example.bookings.service;

import com.example.bookings.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> findAll();

    List<Booking> findByBookingDate(LocalDate date);

    Booking findById(long id);

    Booking save(Booking Booking);

    void deleteById(long BookingId);
}
