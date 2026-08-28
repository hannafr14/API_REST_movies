package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieDTORequest(

    @NotBlank(message = "Movie name cannot be empty")
    @NotNull(message = "Movie name cannot be null")
    String movieName

) {
}
