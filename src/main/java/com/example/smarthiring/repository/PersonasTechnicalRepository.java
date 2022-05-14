package com.example.smarthiring.repository;

import com.example.smarthiring.entity.PersonasTechnical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonasTechnicalRepository extends JpaRepository<PersonasTechnical, Integer> {
    Optional<Set<PersonasTechnical>> findAllByProjectPersonasId(Integer projectPersonasId);

    void deleteAllByProjectPersonasId(Integer projectPersonasId);

    List<PersonasTechnical> findAllByProjectPersonasIdIn(Collection<Integer> projectPersonasIds);

    Boolean existsPersonasTechnicalByCapabilitiesId(Integer capabilityId);
}
