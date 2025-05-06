package su.tease.project.feature.cacheback.presentation.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.usecase.AddBankAction
import su.tease.project.feature.cacheback.presentation.select.bank.BankSelectPage.OnSelectAction

@Parcelize
data class SelectBankState(
    val addedBankPreset: BankPreset? = null
) : State

class SelectBankReducer : Reducer<SelectBankState> {

    override val initState = SelectBankState()

    override fun SelectBankState.onAction(action: PlainAction) = when(action)  {
        is OnSelectAction -> copy(addedBankPreset = null)
        is AddBankAction.OnSaveSuccess -> copy(addedBankPreset = action.bank)
        else -> this
    }
}
