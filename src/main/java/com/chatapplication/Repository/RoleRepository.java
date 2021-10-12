package com.chatapplication.Repository;

import com.chatapplication.Model.entity.Permission;
import com.chatapplication.Model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByStatus(boolean status);

}
