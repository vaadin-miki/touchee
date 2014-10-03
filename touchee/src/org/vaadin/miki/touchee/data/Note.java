package org.vaadin.miki.touchee.data;

import java.util.Date;

import org.vaadin.miki.form.FieldInformation;
import org.vaadin.miki.form.annotations.FormField;

public class Note {

	private User author;
	
	@FormField(info=FieldInformation.REQUIRED)
	private String title;
	
	@FormField(info=FieldInformation.FREE_TEXT)
	private String header;
	
	@FormField(info={FieldInformation.REQUIRED, FieldInformation.FREE_TEXT})
	private String body;
	
	@FormField
	private Date publishDate;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
}
