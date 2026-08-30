package com.example.demo.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import com.example.demo.dtos.MovieDTORequest;
import com.example.demo.dtos.MovieDTOResponse;

import com.example.demo.entities.ActorEntity;
import com.example.demo.entities.GenreEntity;
import com.example.demo.entities.MovieEntity;
import com.example.demo.entities.YearEntity;

import com.example.demo.repositories.ActorRepository;
import com.example.demo.repositories.GenreRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.YearRepository;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ActorNotFoundException;
import com.example.demo.exceptions.GenreNotFoundException;
import com.example.demo.exceptions.MovieNotFoundException;
import com.example.demo.exceptions.YearNotFoundException;
import com.example.demo.mappers.MovieMapper;

@Service
public class MovieServiceImpl implements InterfaceMovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final YearRepository yearRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    public MovieServiceImpl(
            MovieRepository movieRepository, 
            MovieMapper movieMapper,
            YearRepository yearRepository,
            GenreRepository genreRepository,
            ActorRepository actorRepository) {

        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.yearRepository = yearRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
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

            YearEntity year = yearRepository.findById(dto.yearId())
                .orElseThrow(() -> new YearNotFoundException(dto.yearId()));

            Set<GenreEntity> genres = dto.genreIds().stream()
            .map(id -> genreRepository.findById(id)
                    .orElseThrow(() -> new GenreNotFoundException(id)))
            .collect(Collectors.toSet());

            Set<ActorEntity> actors = dto.actorIds().stream()
            .map(actorId -> actorRepository.findById(actorId)
                .orElseThrow(() -> new ActorNotFoundException(actorId)))
            .collect(Collectors.toSet());

            movie.setYear(year);
            movie.setGenres(genres);
            movie.setActors(actors);

        MovieEntity savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieDTOResponse updateMovie(Long id, MovieDTORequest dto) {

        MovieEntity existingMovie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        YearEntity year = yearRepository.findById(dto.yearId())
            .orElseThrow(() -> new YearNotFoundException(dto.yearId()));

        Set<GenreEntity> genres = dto.genreIds().stream()
            .map(genreId -> genreRepository.findById(id)
                    .orElseThrow(() -> new GenreNotFoundException(id)))
            .collect(Collectors.toSet());

        Set<ActorEntity> actors = dto.actorIds().stream()
            .map(actorId -> actorRepository.findById(actorId)
                    .orElseThrow(() -> new ActorNotFoundException(actorId)))
            .collect(Collectors.toSet());

        existingMovie.setMovieName(dto.movieName());
        existingMovie.setYear(year);
        existingMovie.setGenres(genres);
        existingMovie.setActors(actors);

        MovieEntity updatedMovie = movieRepository.save(existingMovie);

        return movieMapper.toResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {

        MovieEntity movie = movieRepository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));

        movieRepository.delete(movie);
    }

    @Override
    public List<MovieDTOResponse> getMoviesByName(String movieName) {
        return movieRepository.findByMovieNameContainingIgnoreCase(movieName)
            .stream()
            .map(movieMapper::toResponse)
            .toList();
    }

    @Override
    public List<MovieDTOResponse> getMoviesByGenre(String genre) {
        return movieRepository.findByGenresGenreContainingIgnoreCase(genre)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }
}
