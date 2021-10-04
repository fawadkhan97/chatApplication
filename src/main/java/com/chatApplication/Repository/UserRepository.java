package com.chatApplication.Repository;

import com.chatApplication.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository

public interface UserRepository extends JpaRepository<User , Long> {
  User findByUserName(String userName);



}
