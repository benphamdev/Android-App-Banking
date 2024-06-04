<<<<<<< HEAD
import org.gradle.api.initialization.resolve.RepositoriesMode

=======
>>>>>>> 143666cbf5f3861bf0ca8eafce72e0622a2ec3a4
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
<<<<<<< HEAD

=======
>>>>>>> 143666cbf5f3861bf0ca8eafce72e0622a2ec3a4
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
<<<<<<< HEAD
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "demoApp"
=======
    }
}

rootProject.name = "activity"
>>>>>>> 143666cbf5f3861bf0ca8eafce72e0622a2ec3a4
include(":app")
 