package su.tease.project.feature.preset.presentation.bank.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions as Save
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage as Select
import su.tease.project.feature.preset.presentation.bank.select.reducer.SelectBankPresetState as S

class SelectBankPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Select.OnSelectAction -> copy(savedCashBackOwnerPreset = null)
        is Save.OnSaveSuccess -> copy(savedCashBackOwnerPreset = action.cashBackOwnerPreset)
        else -> this
    }
}

@Parcelize
data class SelectBankPresetState(
    val savedCashBackOwnerPreset: BankPreset? = null
) : State
