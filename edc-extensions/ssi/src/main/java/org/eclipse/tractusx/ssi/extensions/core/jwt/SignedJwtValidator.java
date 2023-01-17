package org.eclipse.tractusx.ssi.extensions.core.jwt;

import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;

import java.util.Date;

public class SignedJwtValidator {

  private SsiSettings settings;
  private String audience;

  public SignedJwtValidator(SsiSettings settings){
      this.settings = settings;
      this.audience = settings.getDidConnector().toString();
  }

  @SneakyThrows
  public boolean validate(SignedJWT jwt){
    String audience = jwt.getJWTClaimsSet().getAudience().get(0); //Todo Check all audiences
    Date expiryDate = jwt.getJWTClaimsSet().getExpirationTime();
    return isValidAudience(audience) && isNotExpired(expiryDate);
  }

  private boolean isValidAudience(String audience){
    return audience.equals(audience);
  }

  private boolean isNotExpired(Date expiryDate){
    return expiryDate.after(new Date());
  }
}
