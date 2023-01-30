package org.eclipse.tractusx.ssi.extensions.core.resolver.did;

import okhttp3.OkHttpClient;
import org.eclipse.tractusx.ssi.extensions.did.web.resolver.DidWebDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolverRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DidResolverRegistryTest {

  private DidDocumentResolverRegistry didDocumentResolverRegistry;

  @BeforeEach
  public void setUp(){
    didDocumentResolverRegistry = new DidDocumentResolverRegistryImpl();
  }

  @Test
  public void registerTestSuccess(){
    // given
    DidDocumentResolver resolver = new DidWebDocumentResolver(null);
    // when
    didDocumentResolverRegistry.register(resolver);
    var res = didDocumentResolverRegistry.get(resolver.getSupportedMethod());
    // then
    Assertions.assertTrue(res.equals(resolver));
  }

}
