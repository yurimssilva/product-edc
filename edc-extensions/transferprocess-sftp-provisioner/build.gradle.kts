
plugins {
    `maven-publish`
    `java-library`
}

dependencies {
    implementation(project(":edc-extensions:transferprocess-sftp-common"))

    implementation(edc.spi.core)
    implementation(edc.policy.engine)
    implementation(edc.spi.transfer)

    testImplementation(edc.junit)
    testImplementation("org.mockito:mockito-inline:4.2.0")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}
