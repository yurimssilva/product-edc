
plugins {
    `maven-publish`
    `java-library`
}

dependencies {
    implementation(project(":edc-extensions:transferprocess-sftp-common"))
    implementation(edc.spi.core)
    implementation(edc.spi.transfer)
    implementation(edc.spi.policy)
    implementation(edc.spi.dataplane.dataplane)
    implementation(edc.dpf.util)
    implementation(edc.dpf.core)
    implementation(edc.policy.engine)
    implementation(libs.bouncyCastle.bcpkix)

    implementation("org.apache.sshd:sshd-core:2.9.2")
    implementation("org.apache.sshd:sshd-sftp:2.9.2")

    testImplementation(libs.awaitility)
    testImplementation(edc.junit)

    testImplementation(libs.mockito.inline)
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}
