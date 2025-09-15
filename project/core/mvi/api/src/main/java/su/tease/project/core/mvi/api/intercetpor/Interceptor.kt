package su.tease.project.core.mvi.api.intercetpor

import su.tease.project.core.mvi.api.action.PlainAction

interface Interceptor {
    fun intercept(action: PlainAction): List<PlainAction>
}
