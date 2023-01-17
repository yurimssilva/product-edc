package org.eclipse.tractusx.ssi.extensions.core.proof.hash;

import org.eclipse.tractusx.ssi.extensions.core.proof.hash.HashedLinkedData;
import org.eclipse.tractusx.ssi.extensions.core.proof.transform.TransformedLinkedData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LinkedDataHasher {

    public HashedLinkedData hash(TransformedLinkedData transformedLinkedData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            var value = digest.digest(transformedLinkedData.getValue().getBytes());
            return new HashedLinkedData(value);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
