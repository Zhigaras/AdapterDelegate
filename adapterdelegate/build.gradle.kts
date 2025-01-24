import org.jreleaser.model.Active

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jreleaser)
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.zhigaras.adapterdelegate"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures { viewBinding = true }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.recyclerview)
}

version = "1.0.0"

publishing {
    mkdir("${layout.projectDirectory}\\build\\jreleaser")
    mkdir("${layout.projectDirectory}\\build\\staging-deploy")
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.zhigaras"
            artifactId = project.name

            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set("Adapter delegate")
                description.set("Easy recycler view delegates and payloads")
                url.set("https://github.com/Zhigaras/AdapterDelegate")
                inceptionYear.set("2025")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("zhigaras")
                        name.set("Zhigaras Ilya")
                        email.set("zhigaras.dev@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/Zhigaras/AdapterDelegate.git")
                    developerConnection.set("scm:git:ssh://github.com/Zhigaras/AdapterDelegate.git")
                    url.set("https://github.com/Zhigaras/AdapterDelegate")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://central.sonatype.com/api/v1/publisher")
//            setUrl(layout.buildDirectory.dir("staging-deploy"))
            credentials {
                username = "krRhFQOP"
                password = "+9d1JDpjVuF9uJTN3tIViU0fqJo1/rpvaPhQbv3nQ3Ev"
            }
        }
    }
}

jreleaser {
    environment {
        variables.set(File("${layout.projectDirectory}\\.jreleaser\\config.toml"))
    }
    gitRootSearch = true
    project {
        inceptionYear = "2025"
        author("@Zhigaras")
        maintainer("@Zhigaras")
    }
    signing {
        active = Active.ALWAYS
        armored = true
        verify = true
    }
    deploy {
        maven {
            mavenCentral.create("sonatype") {
                active = Active.ALWAYS
                url = "https://central.sonatype.com/api/v1/publisher"
                stagingRepository(layout.buildDirectory.dir("staging-deploy").get().toString())
                setAuthorization("Basic")
                applyMavenCentralRules = false // Wait for fix: https://github.com/kordamp/pomchecker/issues/21
                sign = true
                checksums = true
                sourceJar = true
                javadocJar = true
                retryDelay = 60
            }
        }
    }
}
