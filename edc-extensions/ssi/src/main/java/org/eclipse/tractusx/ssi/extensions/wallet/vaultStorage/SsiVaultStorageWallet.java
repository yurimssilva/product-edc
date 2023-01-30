package org.eclipse.tractusx.ssi.extensions.wallet.vaultStorage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

public class SsiVaultStorageWallet implements VerifiableCredentialWallet {

  private final String Identifier = "VaultStorage";

  private final Vault vault;
  private final SsiSettings settings;

  public SsiVaultStorageWallet(Vault vault, SsiSettings settings) {
    this.vault = vault;
    this.settings = settings;
  }

  @Override
  public String getIdentifier() {
    return Identifier;
  }

  @Override
  public VerifiableCredential getMembershipCredential() {
    String membershipVc = vault.resolveSecret(settings.getMembershipVerifiableCredentialAlias());
    ObjectMapper om = new ObjectMapper();
    VerifiableCredential vc = null;
    try {
      vc = om.readValue(membershipVc, VerifiableCredential.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return vc;
  }

  @Override
  public VerifiableCredential getCredential(String credentialType) {


    return null;
  }


}
