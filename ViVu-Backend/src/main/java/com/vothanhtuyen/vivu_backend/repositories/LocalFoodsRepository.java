package com.vothanhtuyen.vivu_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.LocalFoods;

@Repository
public interface LocalFoodsRepository extends JpaRepository<LocalFoods, Long> {
    Optional<List<LocalFoods>> findAllByLocationsId(Long locationId);
}
