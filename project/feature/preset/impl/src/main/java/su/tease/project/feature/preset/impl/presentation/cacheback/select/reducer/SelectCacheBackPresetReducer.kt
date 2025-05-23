package su.tease.project.feature.preset.impl.presentation.cacheback.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.presentation.cacheback.select.SelectCacheBackPresetPage.OnSelectAction as Select
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetActions.OnSaveSuccess as Save
import su.tease.project.feature.preset.impl.presentation.cacheback.select.reducer.CacheBackPresetInfoDialogAction as Dialog
import su.tease.project.feature.preset.impl.presentation.cacheback.select.reducer.SelectCacheBackPresetState as S

class SelectCacheBackPresetReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is Select -> copy(savedCacheBackPreset = null)
        is Save -> copy(savedCacheBackPreset = action.cacheBankPreset)
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
