package su.tease.project.feature.icon.presentation

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.button.DFButtonType
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.icon.presentation.action.ExternalSelectIconActions
import su.tease.project.feature.icon.presentation.action.SelectIconAction
import su.tease.project.feature.icon.presentation.action.SelectIconActionRequest
import su.tease.project.feature.icon.presentation.action.SelectIconActions
import su.tease.project.feature.icon.presentation.component.IconEditor
import su.tease.project.feature.icon.presentation.reducer.SelectIconState
import su.tease.project.feature.icon.presentation.utils.rememberIconSelector

class SelectIconPage(
    store: Store<*>,
    private val selectIconAction: SelectIconAction,
) : BasePageComponent<SelectIconPage.Target>(store) {

    init {
        dispatch(SelectIconActions.OnInit)
    }

    override fun onFinish() {
        dispatch(ExternalSelectIconActions.OnFinish)
    }

    @Composable
    override fun invoke() {
        val (selectedImageUri, onSelect, onClean) = rememberIconSelector()

        val scale = remember { mutableFloatStateOf(1f) }
        val offset = remember { mutableStateOf(Offset.Zero) }
        val rotation = remember { mutableFloatStateOf(0f) }

        val status = selectAsState(SelectIconState::status)

        DFPage(
            title = stringResource(R.string.Icon_IconSelectPage_title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            Column(
                modifier = Modifier
                    .padding(Theme.sizes.padding8)
                    .padding(top = Theme.sizes.padding6)
                    .padding(WindowInsets.ime.asPaddingValues())
            ) {
                IconEditor(
                    uri = selectedImageUri.value,
                    onScaleChange = { scale.floatValue = it },
                    onOffsetChange = { offset.value = it },
                    onRotationChange = { rotation.floatValue = it },
                    onClick = { onSelect() }
                )
                Spacer(Modifier.weight(1F))
                Actions(status, selectedImageUri, scale, offset, rotation, onClean, onSelect)
            }
        }
    }

    @Composable
    @NonSkippableComposable
    private fun Actions(
        status: State<LoadingStatus>,
        selectedImageUri: MutableState<Uri?>,
        scale: MutableFloatState,
        offset: MutableState<Offset>,
        rotation: MutableFloatState,
        onClean: () -> Unit,
        onSelect: () -> Unit
    ) {
        when (status.value) {
            LoadingStatus.Init -> InitActions(
                selectedImageUri,
                scale,
                offset,
                rotation,
                onClean,
                onSelect
            )

            LoadingStatus.Loading -> Unit
            LoadingStatus.Success -> Unit
            LoadingStatus.Failed -> FailedActions()
        }
    }

    @Composable
    private fun InitActions(
        selectedImageUri: MutableState<Uri?>,
        scale: MutableFloatState,
        offset: MutableState<Offset>,
        rotation: MutableFloatState,
        onClean: () -> Unit,
        onSelect: () -> Unit,
    ) {
        selectedImageUri.value?.let {
            DFButton(
                label = stringResource(R.string.Icon_IconSelectPage_SaveButton_title),
                onClick = { onSaveIcon(it, scale, offset, rotation) },
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            DFButton(
                label = stringResource(R.string.Icon_IconSelectPage_CleanButton_title),
                onClick = { onClean() },
                type = DFButtonType.GRAY,
            )
        } ?: run {
            DFButton(
                label = stringResource(R.string.Icon_IconSelectPage_SelectButton_title),
                onClick = { onSelect() },
            )
        }
    }

    private fun onSaveIcon(
        uri: Uri,
        scale: MutableFloatState,
        offset: MutableState<Offset>,
        rotation: MutableFloatState
    ) {
        dispatch(
            selectIconAction(
                SelectIconActionRequest(
                    uri = uri,
                    scale = scale.floatValue,
                    offset = offset.value,
                    rotation = rotation.floatValue,
                )
            )
        )
    }

    @Composable
    private fun FailedActions() {
        DFButton(
            label = stringResource(R.string.Icon_IconSelectPage_TryAgainButton_title),
            onClick = { dispatch(SelectIconActions.OnInit) },
        )
    }

    companion object {
        operator fun invoke() = Target
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
