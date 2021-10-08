package com.chatapplication.Model.Interface;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatDTO implements Serializable {
    private String answer;
    private String answerDate;
    private String chatID;
    private String question;
    private String questionDate;
    private String updatedAnswerDate;
    private String updatedQuestionDate;

}
