package com.example.demo.mappers;

import java.util.stream.Collectors;

import com.example.demo.entities.GenreEntity;
import com.example.demo.entities.ActorEntity;

import com.example.demo.dtos.MovieDTORequest;
import com.example.demo.dtos.MovieDTOResponse;
import com.example.demo.entities.MovieEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieEntity toEntity(MovieDTORequest dto) {
        return new MovieEntity(dto.movieName());
    }

    public MovieDTOResponse toResponse(MovieEntity movie) {
        return new MovieDTOResponse(
            movie.getIdMovie(),
            movie.getMovieName(),
            movie.getYear() != null ? movie.getYear().getYear() : null,
            movie.getGenres()
                .stream()
                .map(GenreEntity::getGenre)
                .collect(Collectors.toSet()),
            movie.getActors()
                .stream()
                .map(ActorEntity::getActor)
                .collect(Collectors.toSet())
        );
    }
}
