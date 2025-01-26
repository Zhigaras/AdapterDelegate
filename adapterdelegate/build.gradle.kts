import org.jreleaser.model.Active

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jreleaser)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
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

version = "1.0.1"

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
                name = "Adapter delegate"
                description = "Easy recycler view delegates and payloads"
                url = "https://github.com/Zhigaras/AdapterDelegate"
                inceptionYear = "2025"
                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://spdx.org/licenses/Apache-2.0.html"
                    }
                }
                developers {
                    developer {
                        id = "zhigaras"
                        name = "Zhigaras Ilya"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/zhigaras/AdapterDelegate.git"
                    developerConnection = "scm:git:ssh://github.com/zhigaras/AdapterDelegate.git"
                    url = "http://github.com/zhigaras/AdapterDelegate"
                }
            }
        }
    }

    repositories {
        maven {
            setUrl(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    environment {
        variables.set(File("${layout.projectDirectory}\\.jreleaser\\config.toml"))
    }
    gitRootSearch = true
    signing {
        active = Active.ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral.create("sonatype") {
                active = Active.ALWAYS
                url = "https://central.sonatype.com/api/v1/publisher"
                stagingRepository("build/staging-deploy")
                applyMavenCentralRules = false
                sign = true
            }
        }
    }
}
