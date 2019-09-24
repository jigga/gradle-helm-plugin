package org.unbrokendome.gradle.plugins.helm.dsl

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.process.ExecResult
import org.unbrokendome.gradle.plugins.helm.command.GlobalHelmOptions
import org.unbrokendome.gradle.plugins.helm.command.HelmExecProvider
import org.unbrokendome.gradle.plugins.helm.command.HelmExecProviderSupport
import org.unbrokendome.gradle.plugins.helm.command.HelmExecSpec
import org.unbrokendome.gradle.plugins.helm.util.*
import javax.inject.Inject


/**
 * The main Helm DSL extension, accessible using the `helm { ... }` block in build scripts.
 */
interface HelmExtension : HelmExecProvider, GlobalHelmOptions {

    override val executable: Property<String>

    override val debug: Property<Boolean>

    override val home: DirectoryProperty

    /**
     * Address of Tiller, in the format `host:port`.
     *
     * If this property is set, its value will be used to set the `HELM_HOST` environment variable for each
     * Helm invocation.
     */
    val host: Property<String>

    /**
     * Name of the kubeconfig context to use.
     *
     * Corresponds to the `--kube-context` command line option in the Helm CLI.
     */
    val kubeContext: Property<String>

    /**
     * Path to the Kubernetes configuration file.
     *
     * If this property is set, its value will be used to set the `KUBECONFIG` environment variable for each
     * Helm invocation.
     */
    val kubeConfig: RegularFileProperty

    /**
     * Time in seconds to wait for any individual Kubernetes operation (like Jobs for hooks). Default is 300.
     *
     * Corresponds to the `--timeout` command line option in the Helm CLI.
     */
    val timeoutSeconds: Provider<Int>

    /**
     * Base output directory for Helm charts.
     *
     * Defaults to `"${project.buildDir}/helm/charts"`.
     */
    val outputDir: DirectoryProperty
}


private open class DefaultHelmExtension
@Inject constructor(
    project: Project,
    objects: ObjectFactory,
    layout: ProjectLayout
) : HelmExtension {

    @Suppress("LeakingThis")
    private val execProviderSupport = HelmExecProviderSupport(project, this)


    final override val executable: Property<String> =
        objects.property<String>()
            .convention(
                project.providerFromProjectProperty(
                    "helm.executable",
                    defaultValue = "helm", evaluateGString = true
                )
            )


    final override val debug: Property<Boolean> =
        objects.property<Boolean>()
            .convention(project.booleanProviderFromProjectProperty("helm.debug"))


    final override val home: DirectoryProperty =
        objects.directoryProperty()
            .convention(project.dirProviderFromProjectProperty("helm.home", evaluateGString = true))


    final override val host: Property<String> =
        objects.property<String>()
            .convention(project.providerFromProjectProperty("helm.host"))


    final override val kubeContext: Property<String> =
        objects.property<String>()
            .convention(project.providerFromProjectProperty("helm.kubeContext"))


    final override val kubeConfig: RegularFileProperty =
        objects.fileProperty()
            .convention(project.fileProviderFromProjectProperty("helm.kubeConfig", evaluateGString = true))


    final override val timeoutSeconds: Property<Int> =
        objects.property<Int>()
            .convention(project.intProviderFromProjectProperty("helm.timeoutSeconds"))


    final override val extraArgs: ListProperty<String> =
        objects.listProperty<String>().empty()


    final override val outputDir: DirectoryProperty =
        objects.directoryProperty()
            .convention(
                project.coalesceProvider(
                    project.dirProviderFromProjectProperty("helm.outputDir", evaluateGString = true),
                    layout.buildDirectory.dir("helm/charts")
                )
            )


    final override fun execHelm(command: String, subcommand: String?, action: Action<HelmExecSpec>): ExecResult =
        execProviderSupport.execHelm(command, subcommand, action)
}


/**
 * Creates a new [HelmExtension] object using the given project's [ObjectFactory].
 *
 * @receiver the Gradle [Project]
 * @return the created [HelmExtension] object
 */
internal fun Project.createHelmExtension(): HelmExtension =
    objects.newInstance(DefaultHelmExtension::class.java, this)
