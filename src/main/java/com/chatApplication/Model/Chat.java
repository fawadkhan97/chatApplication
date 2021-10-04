package com.chatApplication.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "chats")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
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
}
