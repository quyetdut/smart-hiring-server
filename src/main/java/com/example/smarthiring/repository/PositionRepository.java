package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PositionRepository extends JpaRepository<Position, Integer> {

    Optional<Position> findByName(String name);

    List<Position> findAll();

    Optional<Position> findById(Integer id);

    List<Position> findByRoleName( String roleName) ;

}
