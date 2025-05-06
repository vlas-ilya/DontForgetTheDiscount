package su.tease.project.feature.cacheback.presentation.select.code

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.toIntSafe
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCase
import su.tease.project.feature.cacheback.presentation.select.code.component.CodeSelectPageCodePresets
import su.tease.project.feature.cacheback.presentation.select.code.component.CodeSelectPageInput
import su.tease.project.feature.cacheback.presentation.select.code.component.CodeSelectPageSelectedCodes
import su.tease.project.feature.cacheback.presentation.select.code.component.CodeSelectPateSaveButton

class CodesSelectPage(
    store: Store<*>,
    private val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
    private val addCodeUseCase: AddCodeUseCase,
) : BasePageComponent(store) {

    private val selected = target.selected?.map { it.code.value } ?: emptyList()

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            rootConfig { copy(isFullscreen = true) }
            appConfig { copy(titleRes = R.string.page_select_cache_back_codes_title) }
        }

        val code = remember { mutableStateOf("") }
        val codePresets = remember { mutableStateListOf<String>() }
        val selectedCodes = remember { selected.toMutableStateList() }
        val focusRequester = remember { FocusRequester() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            dictionaryInterceptor.cacheBacksCodes()
                .map { it.code.value }
                .let { codePresets.addAll(it) }
        }

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                        dispatch(addCodeUseCase(target.target, selectedCodes.toList())).join()
                        back()
                    }
                }
            )
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: PersistentList<CacheBackCode>?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: PersistentList<CacheBackCode>
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: PersistentList<CacheBackCode>?,
        ) = Target(T::class.java.name, selected)
    }
}

private const val MIN_MCC_CODE = 1000
private const val MAX_MCC_CODE = 9999
