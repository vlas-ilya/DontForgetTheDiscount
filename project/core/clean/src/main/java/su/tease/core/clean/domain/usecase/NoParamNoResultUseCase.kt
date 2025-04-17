package su.tease.core.clean.domain.usecase

import su.tease.project.core.utils.utils.withDefault

interface NoParamNoResultUseCase {
    suspend fun run()

    suspend operator fun invoke() = withDefault {
        run()
    }
}
