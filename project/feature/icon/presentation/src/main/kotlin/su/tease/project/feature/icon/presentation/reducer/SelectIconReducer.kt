package su.tease.project.feature.icon.presentation.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.icon.presentation.action.SelectIconActions
import su.tease.project.feature.icon.presentation.reducer.SelectIconState as S

class SelectIconReducer : Reducer<S> {
    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is SelectIconActions -> onSelect(action)
        else -> this
    }

    fun S.onSelect(action: SelectIconActions): S = when (action) {
        is SelectIconActions.OnInit -> copy(status = LoadingStatus.Init)
        is SelectIconActions.OnStart -> copy(status = LoadingStatus.Loading)
        is SelectIconActions.OnSave -> copy(status = LoadingStatus.Success)
        is SelectIconActions.OnSaveFail -> copy(status = LoadingStatus.Failed)
        is SelectIconActions.OnFinish -> copy(status = LoadingStatus.Init)
        is SelectIconActions.OnSelect -> copy(status = LoadingStatus.Init)
    }
}

@Parcelize
data class SelectIconState(
    val status: LoadingStatus = LoadingStatus.Init,
) : State
