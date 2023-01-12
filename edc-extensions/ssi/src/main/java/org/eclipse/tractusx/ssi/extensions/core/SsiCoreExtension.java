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
package org.eclipse.tractusx.ssi.extensions.core;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.runtime.metamodel.annotation.Provides;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.web.spi.WebService;
import org.eclipse.tractusx.ssi.extensions.core.jwt.SignedJwtFactory;
import org.eclipse.tractusx.ssi.extensions.did.web.controler.DidWebDocumentController;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettingsFactory;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettingsFactoryImpl;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletRegistry;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWalletService;

import static org.eclipse.tractusx.ssi.extensions.did.web.SsiDidWebExtension.API_DID_WEB_CONTEXT;

@Provides({VerifiableCredentialWalletRegistry.class, VerifiableCredentialWalletService.class})
public class SsiCoreExtension implements ServiceExtension {
    public static final String EXTENSION_NAME = "SSI Core Extension";

    public static final String SETTINGS_WALLET = "edc.ssi.wallet";
    public static final String SETTING_DID_DEFAULT = "did:null:connector";
    public static final String SETTING_DID_CONNECTOR = "edc.ssi.did.connector";
    public static final String SETTING_DID_OPERATOR = "edc.ssi.did.operator";
    public static final String SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD = "edc.ssi.verifiable.presentation.signing.method";
    public static final String SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD_DEFAULT = SignedJwtFactory.SIGNING_METHOD_ES256;
    public static final String SETTING_VERIFIABLE_PRESENTATION_SIGNING_KEY_ALIAS = "edc.ssi.verifiable.presentation.signing.key.alias";

    @Inject
    private Vault vault;

    @Override
    public String name() {
        return EXTENSION_NAME;
    }

    @Override
    public void start() {
        // TODO Check whether configured wallet was registered during initialize phase
        // TODO Check whether verifiable presentation signing key is supported / valid
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        final Monitor monitor = context.getMonitor();

        final SsiSettingsFactory settingsFactory = new SsiSettingsFactoryImpl(monitor, vault, context);
        final SsiSettings settings = settingsFactory.createSettings();
    }
}
