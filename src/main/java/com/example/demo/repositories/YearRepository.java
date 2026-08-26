package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.YearEntity;

public interface YearRepository extends JpaRepository<YearEntity, Long> {

}
