package com.chatapplication.Model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "chats")
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String answer;
    private String CreatedDate;
    private String UpdatedDate;

    public Chat(String question, String answer, String createdDate, String updatedDate) {
        this.question = question;
        this.answer = answer;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
    }

    public Chat(){}
}
