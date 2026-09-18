plugins {
    id("java")
    id("org.openapi.generator")
}
val generatedSourcesDir = layout.buildDirectory.dir("generated")
openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/api-schemas/open-api.yaml")
    outputDir.set(generatedSourcesDir.get().asFile.path)
    apiPackage.set("service.center.api")
    modelPackage.set("service.center.model")
    invokerPackage.set("service.center.invoker")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "skipDefaultInterface" to "true",
            "useTags" to "true",
            "useSpringBoot4" to "true",
            "documentationProvider" to "none",
            "annotationLibrary" to "none",
            "openApiNullable" to "false"
        )
    )
    typeMappings.set(mapOf("DateTime" to "Instant"))
    importMappings.set(mapOf("Instant" to "java.time.Instant"))
}
java {
    sourceSets {
        main {
            java {
                srcDir(generatedSourcesDir.get().file("src/main/java"))
            }
        }
    }
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}



dependencies {
    val springBootVersion: String by project
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

