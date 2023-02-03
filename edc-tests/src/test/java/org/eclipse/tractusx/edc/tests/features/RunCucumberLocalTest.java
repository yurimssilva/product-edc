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

package org.eclipse.tractusx.edc.tests.features;

import static java.lang.String.format;

import io.cucumber.java.BeforeAll;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
// @SelectClasspathResource("org/eclipse/tractusx/edc/tests/features")
@SelectClasspathResource("org/eclipse/tractusx/edc/tests/features/S3FileTransfer.feature")
public class RunCucumberLocalTest {

  /*
  # mvn install first
  mvn install -DskipTests

  minikube start
  helm dependency update edc-tests/src/main/resources/deployment/helm/supporting-infrastructure
  helm install infrastructure edc-tests/src/main/resources/deployment/helm/supporting-infrastructure --namespace tractusx --create-namespace

  # start cx-backend-service (they will start on port 8080 and 8081
  npm start
   */

  @BeforeAll
  public static void beforeAll() {
    var dapsUrl = execute("minikube service ids-daps --url --namespace=tractusx");
    var vaultUrl = execute("minikube service vault --url --namespace=tractusx");

    var runtimes =
        List.of(
            createControlPlaneRuntime(
                "plato",
                10,
                vaultUrl,
                dapsUrl,
                "99:83:A7:17:86:FF:98:93:CE:A0:DD:A1:F1:36:FA:F6:0F:75:0A:23:keyid:99:83:A7:17:86:FF:98:93:CE:A0:DD:A1:F1:36:FA:F6:0F:75:0A:23"),
            createControlPlaneRuntime(
                "sokrates",
                20,
                vaultUrl,
                dapsUrl,
                "E7:07:2D:74:56:66:31:F0:7B:10:EA:B6:03:06:4C:23:7F:ED:A6:65:keyid:E7:07:2D:74:56:66:31:F0:7B:10:EA:B6:03:06:4C:23:7F:ED:A6:65"),
            createDataPlaneRuntime("plato", 11, vaultUrl),
            createDataPlaneRuntime("sokrates", 21, vaultUrl));

    for (var runtime : runtimes) {
      try {
        runtime.start();
      } catch (Exception e) {
        throw new RuntimeException("Failed to start runtime " + runtime.getModuleName(), e);
      }
    }
  }

  private static TestRuntime createControlPlaneRuntime(
      String name, int portPrefix, String vaultUrl, String dapsUrl, String oauth2ClientId) {
    var postgresqlUser = "user";
    var postgresqlPassword = "password";

    var postgresUrl =
        RunCucumberLocalTest.execute(
            format("minikube service %s --url --namespace=tractusx", name + "-postgresql"));
    var postgresHostname = postgresUrl.substring(postgresUrl.indexOf(":") + 1);

    var minio = execute("minikube service minio --url --namespace=tractusx");
    return new TestRuntime(
        "edc-controlplane/edc-controlplane-postgresql-hashicorp-vault",
        name + "-control-plane",
        new HashMap<>() {
          {
            put("web.http.default.port", portPrefix + "991");
            put("web.http.default.path", "/default");
            put("web.http.protocol.port", portPrefix + "992");
            put("web.http.protocol.path", "/protocol");
            put("web.http.management.port", portPrefix + "993");
            put("web.http.management.path", "/management");
            put("web.http.control.port", portPrefix + "994");
            put("web.http.control.path", "/control");
            put("edc.vault.hashicorp.url", vaultUrl);
            put("edc.vault.hashicorp.token", "root");
            put("edc.data.encryption.keys.alias", name + "/data-encryption-aes-keys");
            put("edc.api.auth.key", "password");
            put("ids.webhook.address", format("http://localhost:%d992", portPrefix));
            put("edc.oauth.token.url", dapsUrl + "/token");
            put("edc.oauth.provider.jwks.url", dapsUrl + "/jwks.json");
            put("edc.oauth.provider.audience", "idsc:IDS_CONNECTORS_ALL");
            put(
                "edc.oauth.endpoint.audience",
                format("http://localhost:%d992/protocol/data", portPrefix));
            put("edc.oauth.certificate.alias", format("%s/daps/my-%s-daps-crt", name, name));
            put("edc.oauth.private.key.alias", format("%s/daps/my-%s-daps-key", name, name));
            put("edc.oauth.client.id", oauth2ClientId);
            put("edc.aws.endpoint.override", minio);
            put("edc.receiver.http.endpoint", "http://localhost:8081");

            put("edc.datasource.asset.name", "asset");
            put("edc.datasource.asset.url", format("jdbc:postgresql:%s/edc", postgresHostname));
            put("edc.datasource.asset.user", postgresqlUser);
            put("edc.datasource.asset.password", postgresqlPassword);
            put("edc.datasource.contractdefinition.name", "contractdefinition");
            put(
                "edc.datasource.contractdefinition.url",
                format("jdbc:postgresql:%s/edc", postgresHostname));
            put("edc.datasource.contractdefinition.user", postgresqlUser);
            put("edc.datasource.contractdefinition.password", postgresqlPassword);
            put("edc.datasource.contractnegotiation.name", "contractnegotiation");
            put(
                "edc.datasource.contractnegotiation.url",
                format("jdbc:postgresql:%s/edc", postgresHostname));
            put("edc.datasource.contractnegotiation.user", postgresqlUser);
            put("edc.datasource.contractnegotiation.password", postgresqlPassword);
            put("edc.datasource.policy.name", "policy");
            put("edc.datasource.policy.url", format("jdbc:postgresql:%s/edc", postgresHostname));
            put("edc.datasource.policy.user", postgresqlUser);
            put("edc.datasource.policy.password", postgresqlPassword);
            put("edc.datasource.transferprocess.name", "transferprocess");
            put(
                "edc.datasource.transferprocess.url",
                format("jdbc:postgresql:%s/edc", postgresHostname));
            put("edc.datasource.transferprocess.user", postgresqlUser);
            put("edc.datasource.transferprocess.password", postgresqlPassword);

            put(
                format("edc.dataplane.selector.%splane.url", name),
                format("http://localhost:%d994/control/transfer", portPrefix + 1));
            put(format("edc.dataplane.selector.%splane.sourcetypes", name), "HttpData,AmazonS3");
            put(
                format("edc.dataplane.selector.%splane.destinationtypes", name),
                "HttpProxy,AmazonS3");
            put(
                format("edc.dataplane.selector.%splane.properties", name),
                format("{ \"publicApiUrl\": \"http://localhost:%d995/public\" }", portPrefix + 1));
          }
        });
  }

  private static TestRuntime createDataPlaneRuntime(String name, int portPrefix, String vaultUrl) {
      var minio = execute("minikube service minio --url --namespace=tractusx");
    return new TestRuntime(
        "edc-dataplane/edc-dataplane-hashicorp-vault",
        name + "-data-plane",
        new HashMap<>() {
          {
            put("web.http.default.port", portPrefix + "991");
            put("web.http.default.path", "/default");
            put("web.http.control.port", portPrefix + "994");
            put("web.http.control.path", "/control");
            put("web.http.public.port", portPrefix + "995");
            put("web.http.public.path", "/public");
            put("edc.vault.hashicorp.url", vaultUrl);
            put("edc.vault.hashicorp.token", "root");
              put("edc.aws.endpoint.override", minio);
            put(
                "edc.dataplane.token.validation.endpoint",
                format("http://localhost:%d994/control/token", portPrefix - 1));
              put("aws.accessKeyId", name + "qwert123");
              put("aws.secretAccessKey", name + "qwert123");
          }
        });
  }

  public static String execute(String command) {
    try (var stream = Runtime.getRuntime().exec(command).getInputStream()) {
      var ip = new String(stream.readAllBytes());
      return ip.split("\n")[0];
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
