package com.chatApplication.Repository;
import com.chatApplication.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.CrudRepository;




@Repository
public interface ChatRepository  extends JpaRepository<Chat,Long> {
}
