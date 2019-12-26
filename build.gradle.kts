plugins {
    kotlin("jvm") version "1.3.61"
}

group = "com.growse"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.googlecode.lanterna:lanterna:3.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}