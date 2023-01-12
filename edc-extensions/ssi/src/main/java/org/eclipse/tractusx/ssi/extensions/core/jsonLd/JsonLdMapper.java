package org.eclipse.tractusx.ssi.extensions.core.jsonLd;

import org.eclipse.tractusx.ssi.extensions.core.credentials.SerializedVerifiablePresentation;
import org.eclipse.tractusx.ssi.spi.verifiable.presentation.VerifiablePresentation;

public interface JsonLdMapper {

    SerializedVerifiablePresentation serializePresentation(VerifiablePresentation verifiablePresentation);

    VerifiablePresentation deserializePresentation();

}
