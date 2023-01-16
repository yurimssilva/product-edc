package org.eclipse.tractusx.edc.tests.data;

import java.util.Map;
import lombok.NonNull;
import lombok.Value;

@Value
public class HttpProxySourceDataAddress implements DataAddress {
  @NonNull String baseUrl;
  @NonNull Map<String, String> properties;
}
