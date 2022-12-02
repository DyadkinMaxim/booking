package com.example.bookings.repository;

import com.example.bookings.model.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {

    List<Film> findAll();
}
