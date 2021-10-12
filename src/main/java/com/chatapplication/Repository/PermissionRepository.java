package com.chatapplication.Repository;

import com.chatapplication.Model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    List<Permission> findAllByStatus(boolean status);


}
