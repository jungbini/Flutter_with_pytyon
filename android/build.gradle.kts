plugins {
    id("com.android.application") version "8.7.3" apply false   
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false   
    id("com.chaquo.python") version "16.1.0" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }  
}

val newBuildDir: Directory = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}
subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
