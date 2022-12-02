package com.example.bookings.service;

import com.example.bookings.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(long id);

    Film save(Film Film);

    void deleteById(long FilmId);
}
