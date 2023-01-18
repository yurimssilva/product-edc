package org.eclipse.tractusx.ssi.extensions.core.resolver.did;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.tractusx.ssi.extensions.core.exception.SsiException;
import org.eclipse.tractusx.ssi.spi.did.DidMethod;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolver;
import org.eclipse.tractusx.ssi.spi.did.resolver.DidDocumentResolverRegistry;

public class DidDocumentResolverRegistryImpl implements DidDocumentResolverRegistry {

  private final Map<DidMethod, DidDocumentResolver> resolvers = new HashMap<>();

  @Override
  public DidDocumentResolver get(DidMethod didMethod) {
    if (resolvers.containsKey(didMethod))
      throw new SsiException(String.format("Resolver for method '%s' not found", didMethod));

    return resolvers.get(didMethod);
  }

  @Override
  public void register(DidDocumentResolver resolver) {
    if (resolvers.containsKey(resolver.getSupportedMethod()))
      throw new SsiException(
          String.format(
              "Resolver for method '%s' is already registered", resolver.getSupportedMethod()));
    resolvers.put(resolver.getSupportedMethod(), resolver);
  }

  @Override
  public void unregister(DidDocumentResolver resolver) {
    if (!resolvers.containsKey(resolver.getSupportedMethod()))
      throw new SsiException(
          String.format("Resolver for method '%s' not registered", resolver.getSupportedMethod()));
    resolvers.remove(resolver.getSupportedMethod());
  }
}
