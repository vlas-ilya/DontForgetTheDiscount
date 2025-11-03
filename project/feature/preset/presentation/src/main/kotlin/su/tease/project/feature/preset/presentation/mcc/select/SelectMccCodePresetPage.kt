package su.tease.project.feature.preset.presentation.mcc.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.mcc.component.CodeSelectPageCodePresets
import su.tease.project.feature.preset.presentation.mcc.component.CodeSelectPageInput
import su.tease.project.feature.preset.presentation.mcc.component.CodeSelectPageSelectedCodes
import su.tease.project.feature.preset.presentation.mcc.component.CodeSelectPateSaveButton
import su.tease.project.feature.preset.presentation.mcc.select.action.ExternalSelectMccCodeAction.OnFinish
import su.tease.project.feature.preset.presentation.mcc.select.action.SelectMccCodeAction

class SelectMccCodePresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
    private val selectMccCodeAction: SelectMccCodeAction,
) : BasePageComponent<SelectMccCodePresetPage.Target>(store) {

    private val selected = target.selected?.map { it.code } ?: emptyList()

    override fun onFinish() {
        dispatch(OnFinish)
    }

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val code = remember { mutableStateOf("") }
        val codePresets = remember { mutableStateListOf<String>() }
        val selectedCodes = remember { selected.toMutableStateList() }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            presetInteractor.mccCodePresets()
                .map { it.code }
                .let { codePresets.addAll(it) }
        }

        DFPage(
            title = stringResource(R.string.Presets_SelectMccCodePresetPage_Title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            Column(
                modifier = Modifier
                    .padding(Theme.sizes.padding8)
                    .padding(top = Theme.sizes.padding6)
                    .fillMaxSize()
                    .padding(WindowInsets.ime.asPaddingValues())
            ) {
                CodeSelectPageInput(
                    code = code,
                    focusRequester = focusRequester,
                    maxMccCode = MAX_MCC_CODE,
                    onActionClick = {
                        if (code.value.toIntSafe() in MIN_MCC_CODE..MAX_MCC_CODE) {
                            val value = code.value
                            if (value !in selectedCodes) selectedCodes.add(value)
                            if (value !in codePresets) codePresets.add(value)
                            code.value = ""
                            focusRequester.requestFocus()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(Theme.sizes.padding4))

                CodeSelectPageSelectedCodes(
                    selectedCodes = selectedCodes,
                )

                Spacer(modifier = Modifier.height(Theme.sizes.padding4))

                CodeSelectPageCodePresets(
                    codePresets = codePresets,
                    selectedCodes = selectedCodes,
                    code = code,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    onItemClick = {
                        if (it !in selectedCodes) {
                            code.value = ""
                            selectedCodes.add(it)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(Theme.sizes.padding4))

                CodeSelectPateSaveButton(
                    onClick = { dispatch(selectMccCodeAction(selectedCodes.toList())) }
                )
            }
        }
    }

    @Parcelize
    data class Target(
        val selected: PersistentList<MccCodePreset>?
    ) : NavigationTarget.Page


    companion object {
        operator fun invoke(selected: PersistentList<MccCodePreset>?) = Target(selected)
    }
}

private const val MIN_MCC_CODE = 1000
private const val MAX_MCC_CODE = 9999
