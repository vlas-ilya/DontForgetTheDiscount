package su.tease.project.feature.cacheback.presentation.select.bank

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.icons.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.presentation.select.bank.component.SelectBankPresetPreview

class BankSelectPage(
    store: Store<*>,
    private val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) { rootConfig { copy(isFullscreen = true) } }
        LaunchedEffect(Unit) {
            appConfig {
                copy(
                    titleRes = target.pageTitle,
                    floatingButtons = persistentListOf(
                        DFPageFloatingButton(
                            icon = R.drawable.plus,
                            onClick = { forward(AddBankPage()) }
                        )
                    )
                )
            }
        }

        val banks by memoize { dictionaryInterceptor.banks() }

        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = Theme.sizes.padding4,
            ),
        ) {
            item {
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            }
            banks?.forEach {
                item(key = it.id) {
                    SelectBankPresetPreview(
                        bank = it,
                        onClick = {
                            dispatch(OnSelectAction(target.target, it))
                            back()
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        @StringRes val pageTitle: Int,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            @StringRes pageTitle: Int,
            selected: BankPreset?,
        ) = Target(T::class.java.name, pageTitle, selected)
    }
}
