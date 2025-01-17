package com.vothanhtuyen.vivu_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    
}
