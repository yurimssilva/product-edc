package org.eclipse.tractusx.ssi.extensions.core.exception;

import java.util.List;

public class SsiWalletNotFoundException extends SsiException {

    private final String notFoundWalletIdentifier;
    private final List<String> foundWalletIdentifier;

    public SsiWalletNotFoundException(String notFoundWalletIdentifier, List<String> foundWalletIdentifier) {
        this.notFoundWalletIdentifier = notFoundWalletIdentifier;
        this.foundWalletIdentifier = foundWalletIdentifier;

        // TODO Write nice error message
    }
}
