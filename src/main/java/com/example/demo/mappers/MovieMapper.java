package com.example.demo.mappers;

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
            movie.getMovieName()
        );
    }
}
