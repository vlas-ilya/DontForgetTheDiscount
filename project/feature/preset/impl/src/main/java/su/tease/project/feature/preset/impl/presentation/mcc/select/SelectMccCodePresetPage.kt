package su.tease.project.feature.preset.impl.presentation.mcc.select

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.mcc.component.CodeSelectPageCodePresets
import su.tease.project.feature.preset.impl.presentation.mcc.component.CodeSelectPageInput
import su.tease.project.feature.preset.impl.presentation.mcc.component.CodeSelectPageSelectedCodes
import su.tease.project.feature.preset.impl.presentation.mcc.component.CodeSelectPateSaveButton
import su.tease.project.feature.preset.impl.presentation.mcc.select.action.SelectMccCodeAction

class SelectMccCodePresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInterceptor: PresetInterceptor,
    private val selectMccCodeAction: SelectMccCodeAction,
) : BasePageComponent<SelectMccCodePresetPage.Target>(store) {

    private val selected = target.selected?.map { it.code } ?: emptyList()

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val code = remember { mutableStateOf("") }
        val codePresets = remember { mutableStateListOf<String>() }
        val selectedCodes = remember { selected.toMutableStateList() }
        val focusRequester = remember { FocusRequester() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            presetInterceptor.mccCodePresets()
                .map { it.code }
                .let { codePresets.addAll(it) }
        }

        DFPage(
            title = stringResource(R.string.page_select_cash_back_item_codes_title),
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
                    onClick = {
                        scope.launch {
                            dispatch(selectMccCodeAction(target.target, selectedCodes.toList())).join()
                            back()
                        }
                    }
                )
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: PersistentList<MccCodePreset>?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: PersistentList<MccCodePreset>,
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: PersistentList<MccCodePreset>?,
        ) = Target(T::class.java.name, selected)
    }
}

private const val MIN_MCC_CODE = 1000
private const val MAX_MCC_CODE = 9999
