package com.chatapplication.Model.Interface;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatDTO implements Serializable {
	private long chatId; //Chat ID having a question and answer
	private String question; //Chat question
	private String answer; //Chat answer
	private String questionDate; //Chat question Date
	private String answerDate;  //Chat answer Date
	private String updatedQuestionDate;     //Chat question Updated Date
	private String updatedAnswerDate;
	private boolean status;


}
