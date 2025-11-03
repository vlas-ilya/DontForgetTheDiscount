@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.clean.doman.usecase

import su.tease.project.core.mvi.api.action.Action

interface MviUseCase<Request> {

    fun run(payload: Request): Action

    operator fun invoke(payload: Request): Action = run(payload)
}
