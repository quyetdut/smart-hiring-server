package com.smartdev.iresource.personal.repository;

import com.smartdev.iresource.personal.entity.Awards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AwardRepository extends JpaRepository<Awards, Integer> {
    List<Awards> findAll();

    Optional<Awards> findById(Integer id);

    Optional<Awards> findByName(String name);

    Optional<Awards> deleteAwardsById(Integer id);

}