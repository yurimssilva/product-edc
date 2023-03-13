
plugins {
    `maven-publish`
    `java-library`
}

dependencies {
    implementation(edc.spi.core)
    implementation(edc.spi.transfer)

    testImplementation(libs.awaitility)
    testImplementation(edc.junit)

    testImplementation(edc.core.controlplane)
    testImplementation("org.mockito:mockito-inline:4.2.0")
}
