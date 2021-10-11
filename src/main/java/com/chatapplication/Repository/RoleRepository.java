package com.chatapplication.Repository;

import com.chatapplication.Model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
