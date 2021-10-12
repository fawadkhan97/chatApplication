package com.chatapplication.Repository;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    List<Category> findAllByStatus(boolean status);

}
