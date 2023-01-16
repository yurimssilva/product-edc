package org.eclipse.tractusx.ssi.spi.did;

import lombok.Value;

import java.util.List;

@Value
public class DidDocument {
    Did did;

    String id;

    List<String> context;

    List<DidVerificationMethod> verificationMethods;
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
