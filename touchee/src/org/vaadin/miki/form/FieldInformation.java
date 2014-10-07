package org.vaadin.miki.form;

/**
 * Constants related to common additional field information.
 * 
 * @author miki
 *
 */
public interface FieldInformation {

  /**
   * Indicates that the field is a password type field, rather than text field.
   */
  public static final String PASSWORD = "password";

  /**
   * Indicates that the field is a free-text field, rather than text field.
   */
  public static final String FREE_TEXT = "freetext";

  /**
   * Indicates that the field is required.
   */
  public static final String REQUIRED = "required";

  /**
   * Indicates that the field is required and allows providing an error message.
   * 
   * @author miki
   *
   */
  public static class IsRequired {
    private final String requiredFieldErrorMessage;

    /**
     * Constructs the information object.
     * 
     * @param message
     *          Message to use.
     */
    public IsRequired(String message) {
      this.requiredFieldErrorMessage = message;
    }

    /**
     * Returns error message associated with the information about being required.
     * 
     * @return Error message.
     */
    public String getErrorMessage() {
      return this.requiredFieldErrorMessage;
    }
  }

}
