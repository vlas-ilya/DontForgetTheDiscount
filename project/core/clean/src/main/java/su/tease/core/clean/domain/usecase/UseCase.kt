package su.tease.core.clean.domain.usecase

import su.tease.project.core.utils.utils.withDefault

interface UseCase<Request, Response> {

    suspend fun run(request: Request): Response

    suspend operator fun invoke(request: Request): Response = withDefault {
        run(request)
    }
}
