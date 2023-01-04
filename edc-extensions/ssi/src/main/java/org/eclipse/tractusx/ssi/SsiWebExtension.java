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
package org.eclipse.tractusx.ssi;

import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.tractusx.ssi.setting.SsiSettings;
import org.eclipse.tractusx.ssi.setting.SsiSettingsFactory;
import org.eclipse.tractusx.ssi.setting.SsiSettingsFactoryImpl;


public class SsiWebExtension implements ServiceExtension {

    public static final String EXTENSION_NAME = "SSI Web Extension Extension";

    public static final String SETTING_DID_DEFAULT = "did:null:connector";
    public static final String SETTING_DID_CONNECTOR = "edc.ssi.did.connector";
    public static final String SETTING_DID_OPERATOR = "edc.ssi.did.operator";
    public static final String SETTING_DID_KEY_PRIVATE = "edc.ssi.did.key.private";
    public static final String SETTING_DID_KEY_PRIVATE_ALIAS = "edc.ssi.did.key.private.alias";
    public static final String SETTING_DID_KEY_PUBLIC  ="edc.ssi.did.key.public";
    public static final String SETTING_DID_KEY_PUBLIC_ALIAS  ="edc.ssi.did.key.public.alias";

    private Monitor monitor;

    @Inject
    private Vault vault;

    @Override
    public String name() {
        return EXTENSION_NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        this.monitor = context.getMonitor();

        final SsiSettingsFactory settingsFactory = new SsiSettingsFactoryImpl(monitor, vault, context);
        final SsiSettings settings = settingsFactory.createSettings();
    }
}
