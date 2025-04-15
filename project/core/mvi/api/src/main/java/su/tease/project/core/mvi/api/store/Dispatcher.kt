@file:Suppress("DEPRECATION")
package su.tease.project.core.mvi.api.store

import kotlinx.coroutines.Job
import su.tease.project.core.mvi.api.action.Action

interface Dispatcher {
    fun dispatch(action: Action): Job
}
