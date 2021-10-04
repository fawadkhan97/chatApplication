package com.chatApplication.Repository;
import com.chatApplication.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface ChatRepository  extends JpaRepository<Chat,Long> {

    @Query(value = "select * from Chat chat where chat.user_id = user_id=?",nativeQuery = true)
    Chat findChatByUserID(Long userid);

   // Chat findByUserId(Long userid);

}
