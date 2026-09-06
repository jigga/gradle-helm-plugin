package com.citi.gradle.plugins.helm.dsl.credentials.internal

import groovy.lang.Closure
import org.gradle.api.Action
internal fun <T : Any> Closure<*>.toAction(): Action<T> {
    return Action { target ->
        val c = this@toAction.rehydrate(target, this@toAction.owner, this@toAction.thisObject)
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.call(target)
    }
}
