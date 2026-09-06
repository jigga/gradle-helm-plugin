package org.unbrokendome.gradle.pluginutils.test

import org.gradle.api.Task
import org.gradle.api.internal.TaskInternal
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.internal.operations.BuildOperationDescriptor
import org.gradle.internal.operations.BuildOperationRunner
import org.gradle.workers.WorkerExecutor

fun Task.isSkipped(): Boolean =
    !(this as TaskInternal).onlyIf.isSatisfiedBy(this)

fun Task.execute(
    checkUpToDate: Boolean = true,
    checkOnlyIf: Boolean = true,
    rethrowFailure: Boolean = true
): TaskOutcome {
    val taskInternal = this as TaskInternal
    val projectInternal = taskInternal.project as ProjectInternal
    val workerExecutor = projectInternal.services.get(WorkerExecutor::class.java)
    val buildOperationRunner = projectInternal.services.get(BuildOperationRunner::class.java)

    val operation = buildOperationRunner.start(BuildOperationDescriptor.displayName(taskInternal.name))

    if (checkOnlyIf && !taskInternal.onlyIf.isSatisfiedBy(this)) {
        operation.setResult(null)
        return TaskOutcome.SKIPPED
    }

    if (checkUpToDate) {
        val upToDateSpec = taskInternal.outputs.upToDateSpec
        if (!upToDateSpec.isEmpty && upToDateSpec.isSatisfiedBy(this)) {
            taskInternal.didWork = false
            operation.setResult(null)
            return TaskOutcome.UP_TO_DATE
        }
    }

    try {
        for (action in taskInternal.actions) {
            action.execute(this)
        }
        workerExecutor.await()
        operation.setResult(null)
        return if (taskInternal.didWork) TaskOutcome.SUCCESS else TaskOutcome.UP_TO_DATE
    } catch (e: Exception) {
        operation.failed(e)
        if (rethrowFailure) {
            throw e
        }
        return TaskOutcome.FAILED
    }
}
