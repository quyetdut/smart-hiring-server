package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface LocationRepository extends JpaRepository<Locations, Integer> {

    List<Locations> findAll();

    Optional<Locations> findByCity( String city);

    Optional<Locations> findById( Integer id);
}
