package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MovieDTORequest(

    @NotBlank(message = "Movie name cannot be empty")
    @NotNull(message = "Movie name cannot be null")
    String movieName,

    @NotNull(message = "Year cannot be null")
    Long yearId,

    @NotNull(message = "Genres cannot be null")
    Set<Long> genreIds,

    @NotNull(message = "Actors cannot be null")
    Set<Long> actorIds
) {

}
