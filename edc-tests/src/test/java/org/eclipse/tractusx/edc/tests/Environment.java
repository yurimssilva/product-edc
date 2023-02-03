/*
 * Copyright (c) 2022 Mercedes-Benz Tech Innovation GmbH
 * Copyright (c) 2021,2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.tractusx.edc.tests;

import static java.lang.String.format;
import static org.eclipse.tractusx.edc.tests.Constants.AWS_ACCESS_KEY_ID;
import static org.eclipse.tractusx.edc.tests.Constants.AWS_SECRET_ACCESS_KEY;
import static org.eclipse.tractusx.edc.tests.Constants.BACKEND_SERVICE_BACKEND_API_URL;
import static org.eclipse.tractusx.edc.tests.Constants.DATABASE_PASSWORD;
import static org.eclipse.tractusx.edc.tests.Constants.DATABASE_URL;
import static org.eclipse.tractusx.edc.tests.Constants.DATABASE_USER;
import static org.eclipse.tractusx.edc.tests.Constants.DATA_MANAGEMENT_API_AUTH_KEY;
import static org.eclipse.tractusx.edc.tests.Constants.DATA_MANAGEMENT_URL;
import static org.eclipse.tractusx.edc.tests.Constants.DATA_PLANE_URL;
import static org.eclipse.tractusx.edc.tests.Constants.EDC_AWS_ENDPOINT_OVERRIDE;
import static org.eclipse.tractusx.edc.tests.Constants.IDS_URL;

import java.util.Locale;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.eclipse.tractusx.edc.tests.features.RunCucumberLocalTest;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Environment {
  @NonNull private final String dataManagementAuthKey;
  @NonNull private final String dataManagementUrl;
  @NonNull private final String idsUrl;
  @NonNull private final String dataPlaneUrl;
  @NonNull private final String backendServiceBackendApiUrl;
  @NonNull private final String databaseUrl;
  @NonNull private final String databaseUser;
  @NonNull private final String databasePassword;
  @NonNull private final String awsEndpointOverride;
  @NonNull private final String awsAccessKey;
  @NonNull private final String awsSecretAccessKey;

  public static Environment getEnvironmentFor(String name) {
    if ("local".equalsIgnoreCase(System.getenv("ENVIRONMENT"))) {
      return Environment.local(name);
    } else {
      return Environment.byName(name);
    }
  }

  private static Environment byName(String name) {
    name = name.toUpperCase(Locale.ROOT);

    return Environment.builder()
        .dataManagementUrl(System.getenv(String.join("_", name, DATA_MANAGEMENT_URL)))
        .dataManagementAuthKey(System.getenv(String.join("_", name, DATA_MANAGEMENT_API_AUTH_KEY)))
        .idsUrl(System.getenv(String.join("_", name, IDS_URL)))
        .dataPlaneUrl(System.getenv(String.join("_", name, DATA_PLANE_URL)))
        .backendServiceBackendApiUrl(
            System.getenv(String.join("_", name, BACKEND_SERVICE_BACKEND_API_URL)))
        .databaseUrl(System.getenv(String.join("_", name, DATABASE_URL)))
        .databaseUser(System.getenv(String.join("_", name, DATABASE_USER)))
        .databasePassword(System.getenv(String.join("_", name, DATABASE_PASSWORD)))
        .awsEndpointOverride(System.getenv(EDC_AWS_ENDPOINT_OVERRIDE))
        .awsAccessKey(System.getenv(String.join("_", name, AWS_ACCESS_KEY_ID)))
        .awsSecretAccessKey(System.getenv(String.join("_", name, AWS_SECRET_ACCESS_KEY)))
        .build();
  }

  private static Environment local(String name) {
    var lowerName = name.toLowerCase(Locale.ROOT);

    var portPrefix = lowerName.equals("plato") ? 10 : 20;

    // TODO: move this somewhere else
    var postgresUrl =
        RunCucumberLocalTest.execute(
            format("minikube service %s --url --namespace=tractusx", lowerName + "-postgresql"));

    var minioUrl =
        RunCucumberLocalTest.execute(
            format("minikube service %s --url --namespace=tractusx", "minio"));

    var postgresHostname = postgresUrl.substring(postgresUrl.indexOf(":") + 1);

    return Environment.builder()
        .databaseUrl(format("jdbc:postgresql:%s/edc", postgresHostname))
        .databaseUser("user")
        .databasePassword("password")
        .dataManagementUrl(format("http://localhost:%d993/management", portPrefix))
        .dataManagementAuthKey("password")
        .idsUrl(format("http://localhost:%d992/protocol", portPrefix))
        .dataPlaneUrl(format("http://localhost:%d992/public", portPrefix + 1))
        .backendServiceBackendApiUrl("http://localhost:8081")
        .awsEndpointOverride(minioUrl)
        .awsAccessKey(format("%sqwerty123", lowerName))
        .awsSecretAccessKey(format("%sqwerty123", lowerName))
        .build();
  }
}
