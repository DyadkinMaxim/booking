package com.example.bookings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private long bookingId;

    private int seatNumber;

    private String bookingDate;

    private long filmId;
}