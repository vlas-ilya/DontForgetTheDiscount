package su.tease.project.feature.preset.impl.presentation.cacheback.save.reducer

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
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetError
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetActions as Save
import su.tease.project.feature.preset.impl.presentation.cacheback.save.reducer.SaveCacheBackPresetState as S

class SaveCacheBackPresetReducer : Reducer<S> {

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
            id = action.cacheBackPreset?.id,
            name = action.cacheBackPreset?.name,
            info = action.cacheBackPreset?.info,
            iconPreset = action.cacheBackPreset?.iconPreset,
            bankPreset = action.bankPreset ?: action.cacheBackPreset?.bankPreset,
            mccCodes = action.cacheBackPreset?.mccCodes,
        )
    }

    private fun S.onIconPresetSelect(action: IconSelectPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(iconPreset = action.selected as? CacheBackIconPreset) }

    private fun S.onBankPresetSelect(action: SelectBankPresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(bankPreset = action.selected) }

    private fun S.onSelectMccCodePreset(action: SelectMccCodePresetPage.OnSelectAction) =
        transformIf(action.target.current()) { copy(mccCodes = action.selected) }

    private fun String.current() = this == this@SaveCacheBackPresetReducer::class.java.name
}

@Parcelize
data class SaveCacheBackPresetState(
    val status: LoadingStatus = Init,
    val id: String? = null,
    val name: String? = null,
    val info: String? = null,
    val iconPreset: CacheBackIconPreset? = null,
    val bankPreset: BankPreset? = null,
    val mccCodes: PersistentList<MccCodePreset>? = null,
    val error: SaveCacheBackPresetError? = null,
    val wasValidation: Boolean = false,
) : State
