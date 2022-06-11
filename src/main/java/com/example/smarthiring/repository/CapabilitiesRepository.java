package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Capabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CapabilitiesRepository extends JpaRepository<Capabilities, Integer> {

    List<Capabilities> findAll();

    List<Capabilities> findByIdIn(Collection<Integer> ids);

    Optional<Capabilities> findByName(String name);

    Optional<Capabilities> findById(Integer id);
    List<Capabilities> findAllByIdIn(List<Integer> ids);

}
