package com.example.eindopdracht.repositories;

import com.example.eindopdracht.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}