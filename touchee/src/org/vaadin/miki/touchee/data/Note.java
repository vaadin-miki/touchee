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

  /**
   * Creates an empty note.
   */
  public Note() {
    ;
  }

  /**
   * Creates a note with a given title and body.
   * 
   * @param title
   *          Title of the note.
   * @param body
   *          Body of the note.
   */
  public Note(String title, String body) {
    this();
    this.setTitle(title);
    this.setBody(body);
  }

  /**
   * Returns the note's author.
   * 
   * @return Author of the note.
   */
  public User getAuthor() {
    return this.author;
  }

  /**
   * Sets the note's author.
   * 
   * @param author
   *          New author of the note.
   */
  public void setAuthor(User author) {
    this.author = author;
  }

  /**
   * Returns note's title.
   * 
   * @return Title of the note.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the note's title.
   * 
   * @param title
   *          Title of the note.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the note's header.
   * 
   * @return Header of the note.
   */
  public String getHeader() {
    return this.header;
  }

  /**
   * Sets the header of the note.
   * 
   * @param header
   *          New header.
   */
  public void setHeader(String header) {
    this.header = header;
  }

  /**
   * Returns the body of the note.
   * 
   * @return Note's body.
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Sets the body of the note.
   * 
   * @param body
   *          Note's body.
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Gets note's publishing date.
   * 
   * @return Publishing date of the note.
   */
  public Date getPublishDate() {
    return this.publishDate;
  }

  /**
   * Sets the note's publishing date.
   * 
   * @param publishDate
   *          Publishing date.
   */
  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

}
