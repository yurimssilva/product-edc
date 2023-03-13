
plugins {
    `maven-publish`
    `java-library`
}

dependencies {
    implementation(edc.spi.core)
    testImplementation(edc.junit)

    testImplementation("org.mockito:mockito-inline:4.2.0")
    testImplementation("org.testcontainers:junit-jupiter:+")
}
