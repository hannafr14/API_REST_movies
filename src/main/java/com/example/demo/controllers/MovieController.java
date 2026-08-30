package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.MovieDTORequest;
import com.example.demo.dtos.MovieDTOResponse;
import com.example.demo.services.InterfaceMovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {
    
    private final InterfaceMovieService movieService;

    public MovieController(InterfaceMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public List<MovieDTOResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDTOResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping("")
    public ResponseEntity<MovieDTOResponse> createMovie(
            @Valid @RequestBody MovieDTORequest dto) {

        MovieDTOResponse response = movieService.createMovie(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public MovieDTOResponse updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDTORequest dto) {

        return movieService.updateMovie(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{movieName}")
    public List<MovieDTOResponse> getMoviesByName(@PathVariable String movieName) {
        return movieService.getMoviesByName(movieName);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDTOResponse>> getMoviesByGenre(
            @PathVariable String genre) {

        return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }
}
