package su.tease.project.feature.cashback.presentation.save.bank.save.reducer

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
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.cashback.presentation.save.bank.save.action.SaveBankAccountActions as Save
import su.tease.project.feature.cashback.presentation.save.bank.save.reducer.SaveBankAccountState as S
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage.OnSelectAction as Select

class SaveBankAccountReducer : Reducer<S> {
    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is Save -> onSave(action)
        is Select -> onBankPresetSelect(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnSave -> copy(status = Loading)
        is Save.OnSaveSuccess -> S(status = Success)
        is Save.OnSaveFail -> S(status = Failed)
        is Save.OnInit -> S(
            status = Init,
            id = action.bankAccount?.id,
            bankPreset = action.bankAccount?.bankPreset,
            customName = action.bankAccount?.customName,
        )
    }

    private fun S.onBankPresetSelect(action: Select) =
        transformIf(action.target.current()) {
            copy(
                bankPreset = action.selected,
                customName = customName ?: action.selected?.name
            )
        }

    private fun String.current() = this == this@SaveBankAccountReducer::class.java.name
}

@Parcelize
data class SaveBankAccountState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val bankPreset: BankPreset? = null,
    val customName: String? = null,
) : State
