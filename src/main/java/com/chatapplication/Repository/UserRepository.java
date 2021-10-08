package com.chatapplication.Repository;

import com.chatapplication.Model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserNameAndPassword(String userName, String Password);

}
