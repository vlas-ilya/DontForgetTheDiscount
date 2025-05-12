package su.tease.project.feature.cacheback.presentation.preset.mcc.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase

interface SelectMccCodeAction : MviUseCase<SelectMccCodeRequest> {
    operator fun invoke(
        target: String,
        codes: List<String>,
    ) = invoke(SelectMccCodeRequest(target, codes))
}

@Parcelize
data object OnSelectMccCodeInit : PlainAction

data class SelectMccCodeRequest(
    val target: String,
    val codes: List<String>,
)
