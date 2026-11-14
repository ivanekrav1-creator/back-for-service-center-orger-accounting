rootProject.name = "back-for-service-center-order-accountung"
include("service:service-center",
    "open-api")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        val springBootVersion: String by settings
        val dependencyManagementVersion: String by settings
        val openapiGeneratorVersion: String by settings
        val spotlessVersion: String by settings
        id("com.diffplug.spotless") version spotlessVersion
        id("org.openapi.generator") version openapiGeneratorVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion
    }
}