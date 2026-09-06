package io.github.jigga.gradle.plugins.helm.plugin.test.utils

sealed interface GradleDistribution {
    companion object {
        val all = buildList {
            add(Current)
            addAll(Custom.values())
        }
    }

    object Current : GradleDistribution

    /**
     * Ideally is to have couple the latest distributions from https://gradle.org/releases/.
     *
     * So we will be able to add even beta versions of Gradle in future.
     */
    enum class Custom(val version: String) : GradleDistribution {
        V8_12("8.12.1"),
        V8_5("8.5")
    }
}
