package org.eclipse.tractusx.ssi.spi.did;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.tractusx.ssi.extensions.core.base.Base58Bitcoin;
import org.eclipse.tractusx.ssi.spi.verifiable.MultibaseString;

import java.util.List;

@Value
@Builder
public class DidDocument {
    @NonNull String id;

    @NonNull List<DidVerificationMethod> verificationMethods;

    /**
     *     val id: String,
     *     @SerialName("@context") @JsonProperty("@context")
     *     val context: List<String>,
     *     val alsoKnownAs: String? = null,
     *     @Serializable(AnySerializer::class) val controller: Any? = null,
     *     @SerialName("verificationMethod")
     *     @JsonProperty("verificationMethod")
     *     val verificationMethods: List<DidVerificationMethodDto>? = null,
     *     @SerialName("authentication")
     *     @JsonProperty("authentication")
     *     val authenticationVerificationMethods: List<Any>? = null,
     *     @SerialName("assertionMethod")
     *     @JsonProperty("assertionMethod")
     *     val assertionMethodVerificationMethods: List<Any>? = null,
     *     @SerialName("keyAgreement")
     *     @JsonProperty("keyAgreement")
     *     val keyAgreementVerificationMethods: List<Any>? = null,
     *     @SerialName("capabilityInvocation")
     *     @JsonProperty("capabilityInvocation")
     *     val capabilityInvocationVerificationMethods: List<Any>? = null,
     *     @SerialName("capabilityDelegation")
     *     @JsonProperty("capabilityDelegation")
     *     val capabilityDelegationVerificationMethods: List<Any>? = null,
     *     @SerialName("service")
     *     @JsonProperty("service")
     *     val services: List<DidServiceDto>? = null
     */
}
