package su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.action.SaveCacheBackPresetActions as Save
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.SelectCacheBackPresetPage as Select
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.CacheBackPresetInfoDialogAction as Dialog
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.SelectCacheBackPresetState as S

class SelectCacheBackPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Select.OnSelectAction -> copy(savedCacheBackPreset = null)
        is Save.OnSaveSuccess -> copy(savedCacheBackPreset = action.cacheBankPreset)
        is Dialog -> anCacheBackInfoDialog(action)
        else -> this
    }

    private fun S.anCacheBackInfoDialog(action: Dialog) = when (action) {
        is Dialog.OnShow -> copy(shownCacheBack = action.data)
        is Dialog.OnHide -> copy(shownCacheBack = null)
    }
}

@Parcelize
data class SelectCacheBackPresetState(
    val savedCacheBackPreset: CacheBackPreset? = null,
    val shownCacheBack: Pair<BankPreset, CacheBackPreset>? = null,
) : State

@Parcelize
sealed class CacheBackPresetInfoDialogAction : PlainAction {
    data class OnShow(val data: Pair<BankPreset, CacheBackPreset>?) : Dialog()
    data object OnHide : Dialog()
}
