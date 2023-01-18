package org.eclipse.tractusx.ssi.extensions.core.resolver.key;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;

public class SigningKeyResolver {

  private final Vault vault;
  private final SsiSettings settings;

  public SigningKeyResolver(Vault vault, SsiSettings settings) {
    this.vault = vault;
    this.settings = settings;
  }

  public PrivateKey getSigningKey(String signingMethod) {

    if (!signingMethod.equals(SigningMethod.SIGNING_METHOD_ES256)) {
      throw new RuntimeException("not supported"); // TODO
    }

    final String signingKey =
        vault.resolveSecret(settings.getVerifiablePresentationSigningKeyAlias());
    if (signingKey == null) {
      throw new RuntimeException(); // TODO
    }

    final byte[] signingKeyBytes = signingKey.getBytes();

    final KeyFactory kf;
    try {
      kf = KeyFactory.getInstance("EC");

      return kf.generatePrivate(new PKCS8EncodedKeySpec(signingKeyBytes));

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e); // TODO
    }
  }
}
