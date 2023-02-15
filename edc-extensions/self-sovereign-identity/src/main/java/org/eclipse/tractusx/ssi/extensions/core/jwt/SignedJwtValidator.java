package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;

public class SignedJwtValidator {

  private SsiSettings settings;
  private String audience;

  public SignedJwtValidator(SsiSettings settings) {
    this.settings = settings;
    this.audience = settings.getDidConnector().toString();
  }

  @SneakyThrows
  public boolean validate(SignedJWT jwt) {
    List<String> audiences = jwt.getJWTClaimsSet().getAudience();
    Date expiryDate = jwt.getJWTClaimsSet().getExpirationTime();
    return isValidAudience(audiences) && isNotExpired(expiryDate);
  }

  private boolean isValidAudience(List<String> audiences) {
    boolean result = audiences.stream().anyMatch(x -> x.equals(audience));
    return result;
  }

  private boolean isNotExpired(Date expiryDate) {
    return expiryDate.after(new Date()); // Todo add Timezone
  }
}
