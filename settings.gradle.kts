rootProject.name = "DeepseekTest"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven { url=uri ("https://jitpack.io") }
        maven { url=uri ("https://maven.aliyun.com/repository/releases") }
//        maven { url 'https://maven.aliyun.com/repository/jcenter' }
        maven { url=uri ("https://maven.aliyun.com/repository/google") }
        maven { url=uri ("https://maven.aliyun.com/repository/central") }
        maven { url=uri ("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url=uri ("https://maven.aliyun.com/repository/public") }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url=uri ("https://jitpack.io") }
        maven { url=uri ("https://maven.aliyun.com/repository/releases") }
//        maven { url 'https://maven.aliyun.com/repository/jcenter' }
        maven { url=uri ("https://maven.aliyun.com/repository/google") }
        maven { url=uri ("https://maven.aliyun.com/repository/central") }
        maven { url=uri ("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url=uri ("https://maven.aliyun.com/repository/public") }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")