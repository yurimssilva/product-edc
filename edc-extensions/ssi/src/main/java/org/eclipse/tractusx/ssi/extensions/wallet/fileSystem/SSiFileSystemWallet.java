package org.eclipse.tractusx.ssi.extensions.wallet.fileSystem;

import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

public class SSiFileSystemWallet implements VerifiableCredentialWallet {
    private static final String Identifier = "FileSystem";

    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public VerifiableCredential GetMembershipCredential() {
        return null;
    } // TODO
}
