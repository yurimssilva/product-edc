package org.eclipse.tractusx.ssi.spi.did;

import org.junit.jupiter.api.Test;

public class DidTest {

  @Test
  public void buildDidTestSuccess(){
    // given
    DidMethod didMethod = new DidMethod("testMethod");
    DidMethodIdentifier didMethodIdentifier = new DidMethodIdentifier("testId");
    // when
    Did testDid = new Did(didMethod, didMethodIdentifier);
    // then
    testDid.toString().equals("did:testMethod:testId");
  }

}
