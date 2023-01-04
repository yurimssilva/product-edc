package org.eclipse.tractusx.ssi.exception;

public class JwtParseException extends SsiException {
    public JwtParseException(String message) {
        super(message);
    }

    public JwtParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
