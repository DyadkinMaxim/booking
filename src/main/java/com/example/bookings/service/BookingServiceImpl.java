package com.example.bookings.service;

import com.example.bookings.exception.AlreadyExistException;
import com.example.bookings.exception.NotFoundException;
import com.example.bookings.exception.UnprocessableException;
import com.example.bookings.model.Booking;
import com.example.bookings.repository.BookingRepository;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findByBookingDate(LocalDate date) {
        return bookingRepository.findByBookingDate(date);
    }

    @Override
    public Booking findById(long id) throws NotFoundException {
        return bookingRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No Booking found with id " + id));
    }

    @Transactional
    @Override
    public Booking save(@NonNull Booking newBooking) {
        Booking savedBooking;
        try {
             savedBooking = bookingRepository.save(newBooking);
        } catch(DataIntegrityViolationException ex) {
            throw new UnprocessableException(String.format(
                    "Invalid input data: film ID: %s, booking date: %s, seat number: %s",
                    newBooking.getFilm().getFilmId(),
                    newBooking.getBookingDate(),
                    newBooking.getSeatNumber()
            ));
        }
        return savedBooking;
    }

    @Override
    public void deleteById(long bookingId) {
        bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("No Booking found with id " + bookingId));
        bookingRepository.deleteById(bookingId);
    }
}
