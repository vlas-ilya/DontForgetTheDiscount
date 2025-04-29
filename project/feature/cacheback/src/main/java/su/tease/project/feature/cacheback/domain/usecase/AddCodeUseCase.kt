package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase

interface AddCodeUseCase : MviUseCase<AddCodeUseCaseRequest> {
    operator fun invoke(
        target: String,
        codes: List<String>,
    ) = invoke(AddCodeUseCaseRequest(target, codes))
}

@Parcelize
data object OnAddCodeActionInit : PlainAction

data class AddCodeUseCaseRequest(
    val target: String,
    val codes: List<String>,
)
