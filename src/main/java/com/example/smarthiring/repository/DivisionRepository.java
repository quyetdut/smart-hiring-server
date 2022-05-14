package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DivisionRepository extends JpaRepository<Division, Integer> {

    Optional<Division> findByName(String name);

    Optional<Division> findById(Integer id);

    List<Division> findAll();
}
