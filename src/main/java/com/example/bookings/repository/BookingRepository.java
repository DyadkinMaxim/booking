package com.example.bookings.repository;

import com.example.bookings.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAll();

    List<Booking> findByBookingDate(LocalDate date);
}
