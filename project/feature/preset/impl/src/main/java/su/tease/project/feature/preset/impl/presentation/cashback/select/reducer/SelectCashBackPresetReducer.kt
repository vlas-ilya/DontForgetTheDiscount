package su.tease.project.feature.preset.impl.presentation.cashback.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.presentation.cashback.select.SelectCashBackPresetPage.OnSelectAction as Select
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetActions.OnSaveSuccess as Save
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.CashBackPresetInfoDialogAction as Dialog
import su.tease.project.feature.preset.impl.presentation.cashback.select.reducer.SelectCashBackPresetState as S

class SelectCashBackPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Select -> copy(savedCashBackPreset = null)
        is Save -> copy(savedCashBackPreset = action.cashBankPreset)
        is Dialog -> anCashBackInfoDialog(action)
        else -> this
    }

    private fun S.anCashBackInfoDialog(action: Dialog) = when (action) {
        is Dialog.OnShow -> copy(shownCashBack = action.data)
        is Dialog.OnHide -> copy(shownCashBack = null)
    }
}

@Parcelize
data class SelectCashBackPresetState(
    val savedCashBackPreset: CashBackPreset? = null,
    val shownCashBack: Pair<BankPreset, CashBackPreset>? = null,
) : State

@Parcelize
sealed class CashBackPresetInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<BankPreset, CashBackPreset>?) : Dialog()
    data object OnHide : Dialog()
}
