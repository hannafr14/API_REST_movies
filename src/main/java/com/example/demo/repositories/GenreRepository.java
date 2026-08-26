package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.GenreEntity;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

}
