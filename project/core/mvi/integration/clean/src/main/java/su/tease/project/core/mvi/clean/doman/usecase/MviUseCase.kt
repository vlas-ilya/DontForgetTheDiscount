@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.clean.doman.usecase

import su.tease.project.core.mvi.api.action.Action

interface MviUseCase<Request> {

    fun run(request: Request): Action

    operator fun invoke(request: Request): Action = run(request)
}
