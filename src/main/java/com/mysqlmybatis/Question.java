package com.mysqlmybatis;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

	private int questionId;
	private String content;

	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", content=" + content + "]";
	}

}
