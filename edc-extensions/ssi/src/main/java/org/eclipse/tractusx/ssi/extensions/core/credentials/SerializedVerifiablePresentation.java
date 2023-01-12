package org.eclipse.tractusx.ssi.extensions.core.credentials;

public class SerializedVerifiablePresentation {

    private final String value;

    public SerializedVerifiablePresentation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
