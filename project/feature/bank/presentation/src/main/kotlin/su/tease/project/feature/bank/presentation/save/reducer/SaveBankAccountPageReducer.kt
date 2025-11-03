package su.tease.project.feature.bank.presentation.save.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.bank.domain.entity.BankPreset
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountActions
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountSelectBankPresetActions as BankPresetActions

class SaveBankAccountPageReducer : Reducer<SaveBankAccountState> {

    override val initState = SaveBankAccountState()

    override fun SaveBankAccountState.onAction(action: PlainAction): SaveBankAccountState =
        when (action) {
            is SaveBankAccountActions -> onSave(action)
            is BankPresetActions -> onBankPreset(action)
            else -> this
        }

    private fun SaveBankAccountState.onSave(action: SaveBankAccountActions) = when (action) {
        is SaveBankAccountActions.OnInit -> SaveBankAccountState(action)
        is SaveBankAccountActions.OnSave -> copy(status = Loading)
        is SaveBankAccountActions.OnSaveSuccess -> SaveBankAccountState(status = Success)
        is SaveBankAccountActions.OnSaveFail -> SaveBankAccountState(status = Failed)
    }

    private fun SaveBankAccountState.onBankPreset(action: BankPresetActions) = when (action) {
        is BankPresetActions.OnSelected -> copy(
            bankPreset = action.bankPreset,
            customName = customName ?: action.bankPreset.name
        )
    }
}

@Parcelize
data class SaveBankAccountState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val bankPreset: BankPreset? = null,
    val customName: String? = null,
) : State {
    constructor(action: SaveBankAccountActions.OnInit) : this(
        status = Init,
        id = action.bankAccount?.id,
        bankPreset = action.bankAccount?.preset,
        customName = action.bankAccount?.customName,
    )
}
