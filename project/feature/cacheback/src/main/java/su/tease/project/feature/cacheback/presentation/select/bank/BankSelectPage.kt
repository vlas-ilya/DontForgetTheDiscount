package su.tease.project.feature.cacheback.presentation.select.bank

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.presentation.add.component.BankPresetPreview

class BankSelectPage(
    store: Store<*>,
    private val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    override fun RootContainerConfiguration.configure() {
        isFullscreen = true
    }

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override operator fun invoke() {
        val banks by memoize { dictionaryInterceptor.banks() }

        DFPage(
            title = stringResource(R.string.choose_bank_page),
            onBackPressed = ::back,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = Theme.sizes.padding1)
            ) {
                banks?.forEach {
                    item(key = it.id) {
                        BankPresetPreview(
                            bank = it,
                            onClick = {
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Theme.sizes.padding1),
                        )
                    }
                }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: BankPreset?,
        ) = Target(T::class.java.name, selected)
    }
}
