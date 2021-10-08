package com.chatapplication.Repository;
import com.chatapplication.Model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ChatRepository  extends JpaRepository<Chat,Long> {

}
