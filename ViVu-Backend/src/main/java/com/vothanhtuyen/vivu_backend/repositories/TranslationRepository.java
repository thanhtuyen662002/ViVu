package com.vothanhtuyen.vivu_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Translations;

@Repository
public interface TranslationRepository extends JpaRepository<Translations, Long>{
    Optional<Translations> findByTableNameAndColumnNameAndRowIdAndLanguage(String tableName, String columnName, Long rowId, String language);
    @Query("SELECT t FROM Translations t WHERE t.tableName = :tableName AND t.columnName = :columnName AND LOWER(t.value) = LOWER(:value)")
    Optional<Translations> findByTableNameAndColumnNameAndValue(String tableName, String columnName, String value);
}
