package com.example.demo.services;

import java.util.List;

import com.example.demo.dtos.MovieDTORequest;
import com.example.demo.dtos.MovieDTOResponse;
import com.example.demo.entities.MovieEntity;
import com.example.demo.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import com.example.demo.exceptions.MovieNotFoundException;
import com.example.demo.mappers.MovieMapper;

@Service
public class MovieServiceImpl implements InterfaceMovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTOResponse> getAllMovies() {
        return movieRepository.findAll()
            .stream()
            .map(movieMapper::toResponse)
            .toList();
    }

    @Override
    public MovieDTOResponse getMovieById(Long id) {

        MovieEntity movie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        return movieMapper.toResponse(movie);
    }

    @Override
    public MovieDTOResponse createMovie(MovieDTORequest dto) {

        MovieEntity movie = movieMapper.toEntity(dto);

        MovieEntity savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieDTOResponse updateMovie(Long id, MovieDTORequest dto) {

        MovieEntity existingMovie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        existingMovie.setMovieName(dto.movieName());

        MovieEntity updatedMovie = movieRepository.save(existingMovie);

        return movieMapper.toResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {

        MovieEntity movie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        movieRepository.delete(movie);
    }
}
