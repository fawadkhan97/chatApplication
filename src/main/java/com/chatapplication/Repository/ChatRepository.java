package com.chatapplication.Repository;
import com.chatapplication.Model.entity.Chat;
import com.chatapplication.Model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatRepository  extends JpaRepository<Chat,Long> {
    List<Chat> findAllByStatus(boolean status);

}
