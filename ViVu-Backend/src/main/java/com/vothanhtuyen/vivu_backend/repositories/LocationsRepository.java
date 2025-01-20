package com.vothanhtuyen.vivu_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Locations;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Integer> {
    @Query("SELECT l FROM Locations l WHERE LOWER(l.name) LIKE LOWER(:name)")
    Optional<Locations> findByNameIgnoreCase(@Param("name") String name);

    Optional<Locations> findById(Long id);
}
