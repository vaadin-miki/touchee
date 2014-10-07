package org.vaadin.miki.touchee.data;

import java.util.Date;

import org.vaadin.miki.form.FieldInformation;
import org.vaadin.miki.form.annotations.FormField;
import org.vaadin.miki.form.annotations.ValidateWith;

import com.vaadin.data.validator.EmailValidator;

/**
 * User class - a representation of a user and a base for logging in.
 * 
 * @author miki
 *
 */
public class User {

  @FormField(info = FieldInformation.REQUIRED)
  private String username;

  @FormField(form = "login", info = {FieldInformation.PASSWORD, FieldInformation.REQUIRED})
  private String password;

  @FormField(form = "edit", validate = {@ValidateWith(validator = EmailValidator.class, parameters = "Invalid format of email address.")})
  private String email;

  @FormField(form = "edit")
  private Date dateOfBirth;

  /**
   * Returns username.
   * 
   * @return Name of the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   * 
   * @param username
   *          New username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns current password.
   * 
   * @return Current password, not encrypted.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the new password.
   * 
   * @param password
   *          Password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns user's email address.
   * 
   * @return Email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address of the user.
   * 
   * @param email
   *          Email address to use.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the date of birth.
   * 
   * @return Date of birth.
   */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Sets the date of birth.
   * 
   * @param dateOfBirth
   *          New date of birth.
   */
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

}
