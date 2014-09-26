package org.vaadin.miki.form;

/**
 * Constants related to common additional field information.
 * @author miki
 *
 */
public interface FieldInformation {

	public static final String PASSWORD = "password";
	public static final String FREE_TEXT = "freetext";
	
	public static final String REQUIRED = "required";

	public static class IsRequired {
		private final String requiredFieldErrorMessage;
		
		public IsRequired(String message) {
			this.requiredFieldErrorMessage = message;
		}
		
		public String getErrorMessage() {
			return this.requiredFieldErrorMessage;
		}
	}
	
}
