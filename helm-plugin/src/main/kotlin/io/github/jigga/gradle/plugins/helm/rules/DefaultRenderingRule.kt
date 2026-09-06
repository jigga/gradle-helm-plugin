package io.github.jigga.gradle.plugins.helm.rules

import org.gradle.api.NamedDomainObjectContainer
import io.github.jigga.gradle.plugins.helm.dsl.HelmRendering
import org.gradle.api.Rule


/**
 * A rule that will create a [HelmRendering] named "default".
 */
internal class DefaultRenderingRule(
    private val renderings: NamedDomainObjectContainer<HelmRendering>
) : Rule {

    override fun getDescription(): String {
        return "default rendering"
    }

    override fun apply(domainObjectName: String) {
        if (domainObjectName == HelmRendering.DEFAULT_RENDERING_NAME) {
            renderings.create(HelmRendering.DEFAULT_RENDERING_NAME)
        }
    }
}
