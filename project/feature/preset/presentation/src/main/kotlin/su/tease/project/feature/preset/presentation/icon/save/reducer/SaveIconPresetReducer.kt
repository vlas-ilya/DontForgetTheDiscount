package su.tease.project.feature.preset.presentation.icon.save.reducer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetActions
import su.tease.project.feature.preset.presentation.icon.save.reducer.PageState.Init
import su.tease.project.feature.preset.presentation.icon.save.reducer.PageState.Saving
import su.tease.project.feature.preset.presentation.icon.save.reducer.PageState.Finish
import su.tease.project.feature.preset.presentation.icon.save.reducer.SaveIconPresetState as S

class SaveIconPresetReducer : Reducer<S> {

    override val initState: S = S(Init)

    override fun S.onAction(action: PlainAction): S = when (action) {
        is SaveIconPresetActions -> onSave(action)
        else -> this
    }

    fun S.onSave(action: SaveIconPresetActions): S = when (action) {
        is SaveIconPresetActions.OnInit -> S(Init)
        is SaveIconPresetActions.OnIconSelected -> S(Saving(iconInfo = action.iconInfo))
        is SaveIconPresetActions.OnSaveStart -> onLoadingStatus(LoadingStatus.Loading)
        is SaveIconPresetActions.OnSaveFailed -> onLoadingStatus(LoadingStatus.Failed)
        is SaveIconPresetActions.OnSaveSuccess -> onLoadingStatus(LoadingStatus.Success)
        is SaveIconPresetActions.OnFinish -> S(Finish)
    }

    private fun S.onLoadingStatus(status: LoadingStatus): S = copy(
        pageState = pageState.let { it as? Saving }?.copy(status = status) ?: pageState
    )
}

@Parcelize
data class SaveIconPresetState(
    val pageState: PageState
) : State

@Parcelize
sealed class PageState : Parcelable {
    data object Init : PageState()
    data class Saving(
        val iconInfo: IconInfo,
        val status: LoadingStatus = LoadingStatus.Init
    ) : PageState()
    data object Finish : PageState()
}

@Parcelize
data class IconInfo(
    val iconOwner: IconOwner,
    val path: String,
) : Parcelable
