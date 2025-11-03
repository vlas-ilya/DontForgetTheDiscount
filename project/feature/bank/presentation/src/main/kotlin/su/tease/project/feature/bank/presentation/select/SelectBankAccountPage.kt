package su.tease.project.feature.bank.presentation.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.component.BankAccountsItem
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.info.list.utils.toUi
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage
import su.tease.project.feature.bank.presentation.select.action.CreateBankAccountAction
import su.tease.project.feature.bank.presentation.select.action.ExternalSelectBankAccountActions
import su.tease.project.design.icons.R as RIcons

class SelectBankAccountPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val interceptor: BankAccountInterceptor,
    private val bankPresetIconView: BankPresetIconView,
    private val createBankAccountAction: CreateBankAccountAction,
) : BasePageComponent<SelectBankAccountPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    override fun onFinish() {
        dispatch(ExternalSelectBankAccountActions.OnFinish)
    }

    @Composable
    override fun invoke() {
        val list = memoize { interceptor.list() }
            .map { it?.toUi(bankPresetIconView, store, ::onBankAccountClick) }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { dispatch(createBankAccountAction()) }
                ),
                DFPageFloatingButton(
                    icon = RIcons.drawable.angle_up,
                    onClick = { scrollUp() },
                    type = DFPageFloatingButton.Type.GRAY,
                    isVisible = isScrollTopButtonVisible.value
                )
            )
        }

        DFPage(
            title = stringResource(R.string.Bank_SelectBankAccountPage_Title),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            floatingButtons = floatingButtons,
        ) {
            val banks = list.value ?: run {
                lazyListWrapper.Shimmer(
                    count = SHIMMER_ITEM_COUNT,
                    modifier = Modifier.fillMaxWidth(),
                    itemContent = { BankAccountsItem.Shimmer(it) },
                    verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                    contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                )
                return@DFPage
            }


            lazyListWrapper.Compose(
                count = banks.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = banks::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            )
        }
    }

    private fun onBankAccountClick(bankAccount: BankAccount) {
        dispatch(ExternalSelectBankAccountActions.OnSelected(bankAccount))
        back()
    }

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3