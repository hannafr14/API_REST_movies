package com.example.demo.dtos;

import java.util.Set;

public record MovieDTOResponse(
    Long id,
    String movieName,
    Integer year,
    Set<String> genres,
    Set<String> actors
) {
}
