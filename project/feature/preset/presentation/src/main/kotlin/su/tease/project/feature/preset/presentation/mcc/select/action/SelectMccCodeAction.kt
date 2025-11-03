package su.tease.project.feature.preset.presentation.mcc.select.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.MccCodePreset

interface SelectMccCodeAction : MviUseCase<List<String>>

@Parcelize
sealed class ExternalSelectMccCodeAction : PlainAction {
    data class OnSelected(val mccCodes: PersistentList<MccCodePreset>) : ExternalSelectMccCodeAction()
    data object OnFinish : ExternalSelectMccCodeAction()
}
