package com.example.astrotalk.repository;

import com.example.astrotalk.entity.user.Role;
import com.example.astrotalk.entity.user.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleEnum(RoleEnum role);
}
