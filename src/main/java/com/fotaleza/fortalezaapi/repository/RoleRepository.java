package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);

}
