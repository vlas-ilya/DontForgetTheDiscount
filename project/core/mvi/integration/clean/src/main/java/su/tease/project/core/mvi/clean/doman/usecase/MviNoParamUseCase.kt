@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.clean.doman.usecase

import su.tease.project.core.mvi.api.action.Action

interface MviNoParamUseCase {

    fun run(): Action

    operator fun invoke(): Action = run()
}
