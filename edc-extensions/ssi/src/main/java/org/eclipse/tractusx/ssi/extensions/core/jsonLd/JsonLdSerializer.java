package org.eclipse.tractusx.ssi.extensions.core.jsonLd;

import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

// TODO This hides danubtech
public interface JsonLdSerializer {

    SerializedVerifiablePresentation serializePresentation(VerifiablePresentation verifiablePresentation);

    VerifiablePresentation deserializePresentation(String serializedPresentation);

}
