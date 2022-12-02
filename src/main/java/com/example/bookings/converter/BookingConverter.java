package com.example.bookings.converter;

import com.example.bookings.dto.BookingDTO;
import com.example.bookings.model.Booking;

public interface BookingConverter {

    BookingDTO toDTO(Booking booking);

    Booking toEntity(long filmId, String isoDate, int seatNumber);
}
