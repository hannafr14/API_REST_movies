package com.example.demo.services;

import java.util.List;

import com.example.demo.dtos.MovieDTORequest;
import com.example.demo.dtos.MovieDTOResponse;

public interface InterfaceMovieService {

    List<MovieDTOResponse> getAllMovies();

    MovieDTOResponse getMovieById(Long id);

    MovieDTOResponse createMovie(MovieDTORequest dto);

    MovieDTOResponse updateMovie(Long id, MovieDTORequest dto);

    void deleteMovie(Long id);

    List<MovieDTOResponse> getMoviesByName(String movieName);

    List<MovieDTOResponse> getMoviesByGenre(String genre);
}
