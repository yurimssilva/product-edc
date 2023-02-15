package org.eclipse.tractusx.ssi.extensions.core.exception;

public class SigningKeyException extends SsiException {
  public SigningKeyException(String message) {
    super(message);
  }

  public SigningKeyException(String message, Throwable cause) {
    super(message, cause);
  }
}
