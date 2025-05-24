package su.tease.project.feature.cashback.presentation.save.cashback.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.transformIf
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.cashback.presentation.save.bank.select.SelectBankAccountPage.OnSelectAction as SelectBankAccount
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackActions as Save
import su.tease.project.feature.cashback.presentation.save.cashback.reducer.SaveCashBackState as S
import su.tease.project.feature.preset.api.presentation.cashback.select.SelectCashBackPresetPage.OnSelectAction as SelectCashBackPreset

class SaveCashBackReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is SelectBankAccount -> onSelectBankAccount(action)
        is SelectCashBackPreset -> onSelectCashBackPreset(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnSave -> copy(status = Loading)
        is Save.OnSaveSuccess -> S(status = Success)
        is Save.OnSaveFail -> copy(status = Failed)
        is Save.OnSetDate -> copy(date = action.date)
        is Save.OnInit -> {
            S(
                status = Init,
                id = action.request?.id,
                bankAccount = action.request?.bankAccount,
                cashBackPreset = action.request?.cashBackPreset,
                size = action.request?.size,
                date = action.request?.date,
            )
        }
    }

    private fun S.onSelectBankAccount(action: SelectBankAccount): S {
        return transformIf(action.target.current()) {
            copy(
                bankAccount = action.selected,
                cashBackPreset = cashBackPreset.takeIf { bankAccount?.id == action.selected?.id }
            )
        }
    }

    private fun S.onSelectCashBackPreset(action: SelectCashBackPreset) =
        transformIf(action.target.current()) { copy(cashBackPreset = action.selected) }

    private fun String.current() = this == this@SaveCashBackReducer::class.java.name
}

@Parcelize
data class SaveCashBackState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
    val wasValidation: Boolean = false,
) : State
