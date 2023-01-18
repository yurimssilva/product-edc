package org.eclipse.tractusx.ssi.extensions.wallet.fileSystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.DanubTechMapper;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredential;
import org.eclipse.tractusx.ssi.spi.verifiable.credential.VerifiableCredentialType;
import org.eclipse.tractusx.ssi.spi.wallet.VerifiableCredentialWallet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SsiFileSystemWallet implements VerifiableCredentialWallet {
    private static final String Identifier = "FileSystem";

    private final Path credentialDirectory;

    @Getter(lazy = true)
    private final VerifiableCredential membershipCredential = loadMembershipCredential();

    @Override
    public String getIdentifier() {
        return Identifier;
    }

    private VerifiableCredential loadMembershipCredential() {
        //TODO Throw better exception
        return readCredentials().stream().filter(c -> c.getTypes().stream().anyMatch(VerifiableCredentialType.MEMBERSHIP_CREDENTIAL::equals)).findFirst().orElseThrow();
    }

    @SneakyThrows
    private List<VerifiableCredential> readCredentials() {
        List<Path> paths = new LinkedList<>();

        if (Files.isDirectory(credentialDirectory)) {
            Files.list(credentialDirectory).forEach(paths::add);
        } else {
            paths.add(credentialDirectory);
        }

        return paths.stream().map(this::readCredentialFile).collect(Collectors.toList());
    }

    @SneakyThrows
    private VerifiableCredential readCredentialFile(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            try (InputStreamReader reader = new InputStreamReader(is)) {
                var dtCredential = com.danubetech.verifiablecredentials.VerifiableCredential.fromJson(reader);
                return DanubTechMapper.map(dtCredential);
            }
        }
    }
}
