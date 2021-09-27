package com.chatApplication.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String answer;
    @Column(nullable = false)
    private Date qDate;
    @Column(nullable = false)
    private Date ansDate;
public Chat(){};
    public Chat(long id, String question, String answer, Date qDate, Date ansDate) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.qDate = qDate;
        this.ansDate = ansDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getqDate() {
        return qDate;
    }

    public void setqDate(Date qDate) {
        this.qDate = qDate;
    }

    public Date getAnsDate() {
        return ansDate;
    }

    public void setAnsDate(Date ansDate) {
        this.ansDate = ansDate;
    }
}
