package net.portal.enums;

public class ValidationMessage {

  public enum Validation {
	  
    LoginFailedMessage("Login Failed! Invalid username or password!");
	  
    private String value;

    Validation(String value) {
      this.value = value;
    }

    public String toString() {
      return value;
    }
  }
}
