package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

}
