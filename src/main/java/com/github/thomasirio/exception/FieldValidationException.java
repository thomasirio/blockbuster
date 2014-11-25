package com.github.thomasirio.exception;

/**
 * Created by De Gennaro Tommaso 30 October 2014.
 */

public class FieldValidationException extends Exception {

  public static final String VALUEOF_METHOD_MISSING = "Destination type has not method \"valueOf\"";
  public static final String INVALID_VALUE = "Received an invalid value: ";
  public static final String ID_FIELD_NOT_FOUND = "ID field not found in class: ";
  public static final String INVALID_PAGE = "Invalid page passed";

  public FieldValidationException() { super(); }

  public FieldValidationException(String message, Exception cause) {
    super(message, cause);
  }

  public FieldValidationException(String message, String value) {
    super(message + value);
  }
}
