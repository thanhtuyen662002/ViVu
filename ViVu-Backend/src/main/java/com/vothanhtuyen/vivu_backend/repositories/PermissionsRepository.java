package com.vothanhtuyen.vivu_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    
}
