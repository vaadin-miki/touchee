package org.vaadin.miki.touchee.data;

import java.util.Date;

import org.vaadin.miki.form.FieldInformation;
import org.vaadin.miki.form.annotations.FormField;

public class User {

	@FormField(info=FieldInformation.REQUIRED)
	private String username;
	
	@FormField(form="login", info={FieldInformation.PASSWORD, FieldInformation.REQUIRED})
	private String password;
	
	@FormField(form="edit")
	private String email;
	
	@FormField(form="edit")
	private Date dateOfBirth;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
