package su.tease.project.feature.preset.impl.presentation.cashback.save.reducer

import kotlinx.collections.immutable.PersistentList
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
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetError
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetActions as Save
import su.tease.project.feature.preset.impl.presentation.cashback.save.reducer.SaveCashBackPresetState as S

class SaveCashBackPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Save -> onSave(action)
        is IconSelectPresetPage.OnSelectAction -> onIconPresetSelect(action)
        is SelectBankPresetPage.OnSelectAction -> onBankPresetSelect(action)
        is SelectMccCodePresetPage.OnSelectAction -> onSelectMccCodePreset(action)
        else -> this
    }

    private fun S.onSave(action: Save) = when (action) {
        is Save.OnSave -> copy(status = Loading, error = null)
        is Save.OnSaveFail -> copy(status = Failed, error = action.error)
        is Save.OnSaveSuccess -> S(status = Success)
        is Save.OnInit -> S(
            id = action.cashBackPreset?.id,
            name = action.cashBackPreset?.name,
            info = action.cashBackPreset?.info,
            iconPreset = action.cashBackPreset?.iconPreset,
            bankPreset = action.bankPreset ?: action.cashBackPreset?.bankPreset,
            mccCodes = action.cashBackPreset?.mccCodes,
        )
    }

    private fun S.onIconPresetSelect(action: IconSelectPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(iconPreset = action.selected as? CashBackIconPreset) }

    private fun S.onBankPresetSelect(action: SelectBankPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(bankPreset = action.selected) }

    private fun S.onSelectMccCodePreset(action: SelectMccCodePresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(mccCodes = action.selected) }

    private fun String.current() = this == this@SaveCashBackPresetReducer::class.java.name
}

@Parcelize
data class SaveCashBackPresetState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val name: String? = null,
    val info: String? = null,
    val iconPreset: CashBackIconPreset? = null,
    val bankPreset: BankPreset? = null,
    val mccCodes: PersistentList<MccCodePreset>? = null,
    val error: SaveCashBackPresetError? = null,
    val wasValidation: Boolean = false,
) : State
