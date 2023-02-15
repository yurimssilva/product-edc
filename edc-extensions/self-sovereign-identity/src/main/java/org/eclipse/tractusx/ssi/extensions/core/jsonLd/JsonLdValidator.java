package org.eclipse.tractusx.ssi.extensions.core.jsonLd;

import foundation.identity.jsonld.JsonLDObject;

public interface JsonLdValidator {

  public boolean validate(JsonLDObject jsonLdObject);
}
