package org.eclipse.tractusx.ssi.extensions.core.credentials;

public class SerializedVerifiablePresentation {

  private final String json;

  public SerializedVerifiablePresentation(String json) {
    this.json = json;
  }

  public String getJson() {
    return json;
  }
}
