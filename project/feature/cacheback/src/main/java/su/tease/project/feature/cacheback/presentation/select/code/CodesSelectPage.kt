package su.tease.project.feature.cacheback.presentation.select.code

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCase

class CodesSelectPage(
    store: Store<*>,
    private val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
    private val addCodeUseCase: AddCodeUseCase,
) : BasePageComponent(store) {

    private val selected = target.selected?.map { it.code.value } ?: emptyList()

    @Composable
    override operator fun invoke() {
        val code = remember { mutableStateOf("") }
        val codePresets = remember { mutableStateListOf<String>() }
        val selectedCodes = remember { selected.toMutableStateList() }
        val selectedCodesText = remember { derivedStateOf { selectedCodes.joinToString() } }
        val focusRequester = remember { FocusRequester() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            dictionaryInterceptor.cacheBacksCodes()
                .map { it.code.value }
                .let { codePresets.addAll(it) }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DFTextField(
                text = code,
                onChange = { code.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            DFText(
                text = selectedCodesText.value
            )

            DFButton(
                label = stringResource(R.string.select_code),
                onClick = {
                    val value = code.value
                    if (value !in selectedCodes) selectedCodes.add(value)
                    if (value !in codePresets) codePresets.add(value)
                    code.value = ""
                    focusRequester.requestFocus()
                },
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F)
            ) {
                codePresets
                    .filter { it.contains(code.value) }
                    .forEach {
                        item(key = it) {
                            DFText(
                                text = it,
                                modifier = Modifier.clickable { selectedCodes.add(it) }
                            )
                        }
                    }
            }

            DFButton(
                label = stringResource(R.string.add_codes),
                onClick = {
                    scope.launch {
                        dispatch(addCodeUseCase(target.target, selectedCodes.toList())).join()
                        back()
                    }
                },
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
