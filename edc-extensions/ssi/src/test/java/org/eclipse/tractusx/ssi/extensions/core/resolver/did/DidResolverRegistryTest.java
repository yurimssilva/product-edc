package org.eclipse.tractusx.ssi.extensions.core.resolver.did;

import org.eclipse.tractusx.ssi.extensions.core.exception.SsiException;
import org.eclipse.tractusx.ssi.extensions.did.web.resolver.DidWebDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolverRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @Test
  public void registerTestMultipleDidFail(){
    // given
    DidDocumentResolver resolver = new DidWebDocumentResolver(null);
    String expectedText = "Resolver for method 'web' is already registered";
    // when
    didDocumentResolverRegistry.register(resolver);
    SsiException exception = Assertions.assertThrows(SsiException.class,
            () -> didDocumentResolverRegistry.register(resolver));
    // then
    Assertions.assertTrue(exception.getMessage().contains(expectedText));
  }

  @Test
  public void unregisterTestSuccess(){
    // given
    DidDocumentResolver resolver = new DidWebDocumentResolver(null);
    String expectedText = "Resolver for method 'web' not registered";
    // when
    didDocumentResolverRegistry.register(resolver);
    didDocumentResolverRegistry.unregister(resolver);
    SsiException exception = Assertions.assertThrows(SsiException.class,
            () -> didDocumentResolverRegistry.unregister(resolver));
    didDocumentResolverRegistry.register(resolver);
    var res = didDocumentResolverRegistry.get(resolver.getSupportedMethod());
    // then
    Assertions.assertTrue(exception.getMessage().contains(expectedText));
    Assertions.assertTrue(res.equals(resolver));
  }

  @Test
  public void unregisterTestMultipleDidFail(){
    // given
    DidDocumentResolver resolver = new DidWebDocumentResolver(null);
    String expectedText = "Resolver for method 'web' not registered";
    // when
    SsiException exception = Assertions.assertThrows(SsiException.class,
            () -> didDocumentResolverRegistry.unregister(resolver));
    // then
    Assertions.assertTrue(exception.getMessage().contains(expectedText));
  }

}
