package su.tease.project.feature.cashback.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

interface SaveCashBackSelectCashBackPresetAction : MviUseCase<CashBackOwnerPreset>

@Parcelize
sealed class SaveCashBackSelectCashBackPresetActions : PlainAction {
    data class OnSelected(val cashBackPreset: CashBackPreset) :
        SaveCashBackSelectCashBackPresetActions()
}
