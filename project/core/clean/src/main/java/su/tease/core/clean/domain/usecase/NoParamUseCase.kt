package su.tease.core.clean.domain.usecase

import su.tease.project.core.utils.utils.withDefault

interface NoParamUseCase<Response> {
    suspend fun run(): Response

    suspend operator fun invoke(): Response = withDefault {
        run()
    }
}
