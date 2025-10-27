package su.tease.project.feature.bank.presentation.info.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.and
import su.tease.project.core.utils.utils.or
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoAction
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoActions
import su.tease.project.feature.bank.presentation.info.list.page.BankAccountsInfoPageFailed
import su.tease.project.feature.bank.presentation.info.list.page.BankAccountsInfoPageInit
import su.tease.project.feature.bank.presentation.info.list.page.BankAccountsInfoPageLoading
import su.tease.project.feature.bank.presentation.info.list.page.BankAccountsInfoPageSuccess
import su.tease.project.feature.bank.presentation.info.list.reducer.BankAccountsInfoPageState
import su.tease.project.feature.bank.presentation.info.list.utils.toUi
import su.tease.project.feature.bank.presentation.info.save.SaveBankAccountInfoFeature
import su.tease.project.feature.bank.presentation.list.hasPrevPageSelector
import su.tease.project.design.icons.R as RIcons

class BankAccountsInfoPage(
    store: Store<*>,
    private val loadBankAccountsInfoAction: LoadBankAccountsInfoAction,
    private val resourceProvider: ResourceProvider,
    private val bankPresetIconView: BankPresetIconView,
) : BasePageComponent<BankAccountsInfoPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    init {
        dispatch(LoadBankAccountsInfoActions.OnLoad)
    }

    @Composable
    override fun invoke() {
        val status = selectAsState(BankAccountsInfoPageState::status)
        val list = selectAsState(BankAccountsInfoPageState::list)
            .map { it.toUi(bankPresetIconView, store, ::onBankAccountClick) }

        val isAddButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success || list.value.isNotEmpty()
            }
        }

        val (isScrollTopButtonVisible, _, _, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = { SaveBankAccountInfoFeature().forward() },
                        isVisible = isAddButtonVisible.value
                    ),
                    DFPageFloatingButton(
                        icon = RIcons.drawable.angle_up,
                        onClick = { scrollUp() },
                        type = DFPageFloatingButton.Type.GRAY,
                        isVisible = isScrollTopButtonVisible.value
                    )
                )
            }
        }

        LaunchedEffect(this) { dispatch(loadBankAccountsInfoAction()) }
        DFPage(
            title = stringResource(R.string.Bank_InfoListBankPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            showBackButton = selectAsState(hasPrevPageSelector).value,
        ) {
            val state = status.value
            when {
                state == LoadingStatus.Init -> BankAccountsInfoPageInit(lazyListWrapper)
                state == LoadingStatus.Loading && list.value.isEmpty() -> BankAccountsInfoPageLoading(lazyListWrapper)
                state == LoadingStatus.Failed -> BankAccountsInfoPageFailed({ onTryAgain() })
                else -> BankAccountsInfoPageSuccess(list, lazyListWrapper,)
            }
        }
    }

    private fun onBankAccountClick(bankAccount: BankAccount) =
        SaveBankAccountInfoFeature(bankAccount).forward()

    private fun onTryAgain() = dispatch(loadBankAccountsInfoAction()).unit()

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
