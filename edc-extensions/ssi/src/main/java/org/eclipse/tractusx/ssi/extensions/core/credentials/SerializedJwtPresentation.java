package org.eclipse.tractusx.ssi.extensions.core.credentials;

public class SerializedJwtPresentation {

    private final String value;

    public SerializedJwtPresentation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
