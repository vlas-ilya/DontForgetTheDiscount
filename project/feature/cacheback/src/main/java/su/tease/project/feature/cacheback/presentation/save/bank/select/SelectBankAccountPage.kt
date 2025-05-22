package su.tease.project.feature.cacheback.presentation.save.bank.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cacheback.presentation.preset.bank.component.SelectBankPresetPreview
import su.tease.project.feature.cacheback.presentation.save.bank.save.SaveBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.bank.select.reducer.SelectBankAccountState
import su.tease.project.design.icons.R as RIcons

class SelectBankAccountPage(
    store: Store<*>,
    private val target: Target,
    private val bankAccountInterceptor: BankAccountInterceptor,
) : BasePageComponent<SelectBankAccountPage.Target>(store) {

    @Composable
    override fun invoke() {
        val banks by memoize { bankAccountInterceptor.list() }
        val savedBankAccount = selectAsState(SelectBankAccountState::savedBankAccount).value

        LaunchedEffect(savedBankAccount) {
            if (savedBankAccount != null) {
                dispatch(OnSelectAction(target.target, savedBankAccount))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveBankAccountPage().forward() }
                )
            )
        }

        DFPage(
            title = stringResource(R.string.page_select_bank_account_title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            floatingButtons = floatingButtons,
        ) {
            LazyColumn {
                if (banks == null) {
                    item { SelectBankAccountShimmer() }
                    return@LazyColumn
                }

                item { Spacer(modifier = Modifier.height(Theme.sizes.padding4)) }

                banks?.forEach {
                    item(key = it.id) {
                        val bankPreset = remember(it) { it.bankPreset.copy(name = it.customName) }
                        SelectBankPresetPreview(
                            bankPreset = bankPreset,
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
    }

    @Composable
    private fun SelectBankAccountShimmer(modifier: Modifier = Modifier) {
        Shimmer(
            modifier = modifier,
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding8))
                repeat(SHIMMER_ITEM_COUNT) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Theme.sizes.padding2)
                            .clip(RoundedCornerShape(Theme.sizes.round12))
                            .fillMaxWidth()
                            .height(Theme.sizes.size42)
                            .background(Theme.colors.shimmer)
                    )
                }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankAccount?,
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke() = Target(T::class.java.name)
    }
}

private const val SHIMMER_ITEM_COUNT = 20
