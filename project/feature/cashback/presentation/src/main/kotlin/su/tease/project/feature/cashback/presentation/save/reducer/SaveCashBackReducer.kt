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
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackActions as Save
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerActionActions as Owner
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetActions as Preset

class SaveCashBackReducer : Reducer<SaveCashBackState> {

    override val initState = SaveCashBackState()

    override fun SaveCashBackState.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is Owner -> onOwner(action)
        is Preset -> onPreset(action)
        else -> this
    }

    private fun SaveCashBackState.onSave(action: Save) = when (action) {
        is Save.OnInit -> SaveCashBackState(action)
        is Save.OnSetDate -> copy(date = action.date)
        is Save.OnSave -> copy(status = Loading)
        is Save.OnSaved -> copy(status = Success)
        is Save.OnSaveFail -> copy(status = Failed)
    }

    private fun SaveCashBackState.onOwner(action: Owner) = when (action) {
        is Owner.OnSelected -> copy(cashBackOwner = action.cashBackOwner)
    }

    private fun SaveCashBackState.onPreset(action: Preset) = when (action) {
        is Preset.OnSelected -> copy(cashBackPreset = action.cashBackPreset)
    }
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
) : State {
    constructor(action: Save.OnInit) : this(
        status = Init,
        id = action.id,
        cashBackOwner = action.owner,
        cashBackPreset = action.preset,
        size = action.size,
        date = action.date,
    )
}
