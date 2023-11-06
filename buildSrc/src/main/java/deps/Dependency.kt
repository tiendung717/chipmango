package deps

import org.gradle.api.artifacts.dsl.DependencyHandler

abstract class Dependency {
    open fun implementations(): List<String> = emptyList()
    open fun debugImplementations(): List<String> = emptyList()
    open fun kapt(): List<String> = emptyList()
    open fun api(): List<String> = emptyList()
    open fun platformImplementations(): List<String> = emptyList()
    open fun testImplementations(): List<String> = emptyList()
}

fun DependencyHandler.dependOn(vararg dependencies: Dependency) {
    dependencies.forEach { d ->
        d.implementations().forEach {
            add("implementation", it)
        }
        d.debugImplementations().forEach {
            add("debugImplementation", it)
        }
        d.kapt().forEach {
            add("kapt", it)
        }
        d.api().forEach {
            add("api", it)
        }
        d.platformImplementations().forEach {
            add("implementation", platform(it))
        }
        d.testImplementations().forEach {
            add("testImplementation", it)
        }
    }
}
