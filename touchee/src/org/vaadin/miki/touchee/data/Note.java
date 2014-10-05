package org.vaadin.miki.touchee.data;

import java.util.Date;

import org.vaadin.miki.form.FieldInformation;
import org.vaadin.miki.form.annotations.FormField;

/**
 * A note, with a title, body and the like.
 * 
 * @author miki
 *
 */
public class Note {

  private User author;

  @FormField(info = FieldInformation.REQUIRED)
  private String title;

  @FormField(info = FieldInformation.FREE_TEXT)
  private String header;

  @FormField(info = {FieldInformation.REQUIRED, FieldInformation.FREE_TEXT})
  private String body;

  @FormField
  private Date publishDate;

  public Note() {
    ;
  }

  public Note(String title, String body) {
    this();
    this.setTitle(title);
    this.setBody(body);
  }

  public User getAuthor() {
    return this.author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHeader() {
    return this.header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Date getPublishDate() {
    return this.publishDate;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

}
