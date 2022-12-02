package com.example.bookings.service;

import com.example.bookings.exception.AlreadyExistException;
import com.example.bookings.exception.NotFoundException;
import com.example.bookings.model.Film;
import com.example.bookings.repository.FilmRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film findById(long id) throws NotFoundException {
        return filmRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No film found with id " + id));
    }

    @Transactional
    @Override
    public Film save(@NonNull Film newFilm) {
        filmRepository.findById(newFilm.getFilmId()).ifPresent(
                film -> {
                    throw new AlreadyExistException("Already exisits film with id "
                            + newFilm.getFilmId());
                });
        return filmRepository.save(newFilm);
    }

    @Override
    public void deleteById(long filmId) {
        filmRepository.findById(filmId).orElseThrow(
                () -> new NotFoundException("No film found with id " + filmId));
        filmRepository.deleteById(filmId);
    }
}
