package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ActorEntity;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {

}
