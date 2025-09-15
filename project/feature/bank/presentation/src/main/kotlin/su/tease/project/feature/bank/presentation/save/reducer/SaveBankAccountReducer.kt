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
import su.tease.project.feature.bank.presentation.dependencies.navigation.SelectBankPresetPage.OnSelectAction as Select

class SaveBankAccountReducer : Reducer<SaveBankAccountState> {
    override val initState = SaveBankAccountState()

    override fun SaveBankAccountState.onAction(action: PlainAction): SaveBankAccountState =
        when (action) {
            is SaveBankAccountActions -> onSave(action)
            is Select -> onBankPresetSelect(action)
            else -> this
        }

    private fun SaveBankAccountState.onSave(action: SaveBankAccountActions) = when (action) {
        is SaveBankAccountActions.OnSave -> copy(status = Loading)
        is SaveBankAccountActions.OnSaveSuccess -> SaveBankAccountState(status = Success)
        is SaveBankAccountActions.OnSaveFail -> SaveBankAccountState(status = Failed)
        is SaveBankAccountActions.OnInit -> SaveBankAccountState(
            status = Init,
            id = action.bankAccount?.id,
            ownerPreset = action.bankAccount?.preset,
            customName = action.bankAccount?.customName,
        )
    }

    private fun SaveBankAccountState.onBankPresetSelect(action: Select) = copy(
        ownerPreset = action.selected,
        customName = customName ?: action.selected?.name
    )
}

@Parcelize
data class SaveBankAccountState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val ownerPreset: BankPreset? = null,
    val customName: String? = null,
) : State
