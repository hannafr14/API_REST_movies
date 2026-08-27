package com.example.demo.services;

import java.util.List;
import com.example.demo.entities.MovieEntity;
import com.example.demo.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.MovieNotFoundException;

@Service
public class MovieServiceImpl implements InterfaceMovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public MovieEntity getMovieById(Long id) {
        return movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public MovieEntity createMovie(MovieEntity movie) {
        return movieRepository.save(movie);
    }

    @Override
    public MovieEntity updateMovie(Long id, MovieEntity movie) {

        MovieEntity existingMovie = getMovieById(id);

        existingMovie.setMovieName(movie.getMovieName());
        existingMovie.setYear(movie.getYear());
        existingMovie.setGenres(movie.getGenres());
        existingMovie.setActors(movie.getActors());

        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        MovieEntity movie = getMovieById(id);
        movieRepository.delete(movie);
    }
}
