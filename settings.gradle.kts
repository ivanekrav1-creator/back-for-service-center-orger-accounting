rootProject.name = "back-for-service-center-order-accountung"
include("service:service-center")
include("open-api")
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        val springBootVersion = providers.gradleProperty("springBootVersion").get()
        val dependencyManagementVersion = providers.gradleProperty("dependencyManagementVersion").get()
        val openapiGeneratorVersion = providers.gradleProperty("openapiGeneratorVersion").get()
        val spotlessVersion = providers.gradleProperty("spotlessVersion").get()
        id("com.diffplug.spotless") version spotlessVersion
        id("org.openapi.generator") version openapiGeneratorVersion
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion
    }
}