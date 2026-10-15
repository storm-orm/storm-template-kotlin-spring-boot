import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    id("org.springframework.boot") version "4.1.0"
    // The Storm plugin imports the BOM, adds storm-kotlin and storm-core, applies KSP and wires
    // the metamodel processor to it, and selects the compiler-plugin variant matching Kotlin.
    // KSP comes with the plugin from Kotlin 2.3 on.
    id("st.orm") version "1.15.0"
}

group = "com.example"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")

    // The starter builds the ORMTemplate from the DataSource, finds the repositories and binds the
    // storm.* properties; storm-jackson3 serializes Ref fields.
    implementation("st.orm:storm-kotlin-spring-boot-starter")
    implementation("st.orm:storm-jackson3")

    // The application runs on H2 out of the box and switches to PostgreSQL through the postgres
    // profile alone, so both dialect modules and both drivers are on the runtime path.
    runtimeOnly("st.orm:storm-h2")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("st.orm:storm-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // @StormTest runs each test on a database of its own and rolls it back afterwards.
    testImplementation("st.orm:storm-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("st.orm:storm-h2")
    testRuntimeOnly("com.h2database:h2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
}
