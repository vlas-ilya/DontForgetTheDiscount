package su.tease.project.feature.cashback.presentation.save.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.presentation.dependencies.navigation.SelectCashBackOwnerPage
import su.tease.project.feature.cashback.presentation.dependencies.navigation.SelectCashBackPresetPage
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackActions

class SaveCashBackReducer : Reducer<SaveCashBackState> {

    override val initState = SaveCashBackState()

    override fun SaveCashBackState.onAction(action: PlainAction) = when (action) {
        is SaveCashBackActions -> onSave(action)
        is SelectCashBackOwnerPage.OnSelectAction -> onSelectBankAccount(action)
        is SelectCashBackPresetPage.OnSelectAction -> onSelectCashBackPreset(action)
        else -> this
    }

    private fun SaveCashBackState.onSave(action: SaveCashBackActions) = when (action) {
        is SaveCashBackActions.OnSave -> copy(status = Loading)
        is SaveCashBackActions.OnSaveSuccess -> SaveCashBackState(status = Success)
        is SaveCashBackActions.OnSaveFail -> copy(status = Failed)
        is SaveCashBackActions.OnSetDate -> copy(date = action.date)
        is SaveCashBackActions.OnInit -> {
            SaveCashBackState(
                status = Init,
                id = action.id,
                cashBackOwner = action.owner,
                cashBackPreset = action.preset,
                size = action.size,
                date = action.date,
            )
        }
    }

    private fun SaveCashBackState.onSelectBankAccount(action: SelectCashBackOwnerPage.OnSelectAction) =
        copy(
            cashBackOwner = action.selected,
            cashBackPreset = cashBackPreset.takeIf { cashBackOwner?.id == action.selected?.id }
        )

    private fun SaveCashBackState.onSelectCashBackPreset(action: SelectCashBackPresetPage.OnSelectAction) =
        copy(cashBackPreset = action.selected)
}

@Parcelize
data class SaveCashBackState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val cashBackOwner: CashBackOwner? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
    val wasValidation: Boolean = false,
) : State
