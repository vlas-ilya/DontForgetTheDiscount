package su.tease.core.clean.domain.usecase

import su.tease.project.core.utils.utils.withDefault

interface NoResultUseCase<Request> {
    suspend fun run(request: Request)

    suspend operator fun invoke(request: Request) = withDefault {
        run(request)
    }
}
