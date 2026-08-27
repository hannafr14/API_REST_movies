package com.example.demo.services;

import com.example.demo.entities.MovieEntity;
import java.util.List;

public interface InterfaceMovieService {
    List<MovieEntity> getAllMovies();

    MovieEntity getMovieById(Long id);

    MovieEntity createMovie(MovieEntity movie);

    MovieEntity updateMovie(Long id, MovieEntity movie);

    void deleteMovie(Long id);
}
