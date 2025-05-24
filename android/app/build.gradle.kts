plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.chaquo.python")

    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")    

}

android {
    namespace = "com.example.myapp"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.myapp"
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        // minSdk = flutter.minSdkVersion
        minSdk = 24
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }

        flavorDimensions += "pyVersion"
        productFlavors {
            create("py312") { dimension = "pyVersion" }
        }
    }

    buildTypes {
        release {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}

chaquopy {
    productFlavors {
        getByName("py312") { version = "3.12" }
    }
    defaultConfig {
        buildPython("/opt/homebrew/Caskroom/miniconda/base/bin/python")
        pip {
//            install("numpy")
//            install("qrcode")
//            install("Pillow")
//            install("pytz")
//            install("datetime")
//            install("configparser")
//            install("pymysql")
//            install("pandas")
//            install("astral")
//            install("scipy")      // 불가능
//            install("cosinorpy") // 불가능(scipy 때문)
        }
    }
}
