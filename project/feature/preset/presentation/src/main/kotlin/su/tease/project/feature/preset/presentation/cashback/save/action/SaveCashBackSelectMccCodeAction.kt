package su.tease.project.feature.preset.presentation.cashback.save.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.MccCodePreset

interface SaveCashBackSelectMccCodeAction : MviUseCase<PersistentList<MccCodePreset>?>

@Parcelize
sealed class SaveCashBackSelectMccCodeActions : PlainAction {
    data class OnSelected(val mccCodes: PersistentList<MccCodePreset>) :
        SaveCashBackSelectMccCodeActions()
}
