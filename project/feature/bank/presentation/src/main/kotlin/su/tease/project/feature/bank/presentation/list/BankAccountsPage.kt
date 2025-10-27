package su.tease.project.feature.bank.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.NavigationState
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.and
import su.tease.project.core.utils.utils.or
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.dropdown.DFDropdownMenu
import su.tease.project.design.component.controls.list.LazyListItems
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.entity.defaultCashBackDate
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.dependencies.navigation.SaveCashBackPage
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackInfoDialogView
import su.tease.project.feature.bank.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.bank.presentation.dependencies.view.Compose
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsAction
import su.tease.project.feature.bank.presentation.list.action.LoadBankAccountsActions
import su.tease.project.feature.bank.presentation.list.page.BankAccountsPageFailed
import su.tease.project.feature.bank.presentation.list.page.BankAccountsPageInit
import su.tease.project.feature.bank.presentation.list.page.BankAccountsPageLoading
import su.tease.project.feature.bank.presentation.list.page.BankAccountsPageSuccess
import su.tease.project.feature.bank.presentation.list.reducer.BankAccountsState
import su.tease.project.feature.bank.presentation.list.reducer.CashBackInfoDialogAction
import su.tease.project.feature.bank.presentation.list.utils.toCashBackDate
import su.tease.project.feature.bank.presentation.list.utils.toMonthYear
import su.tease.project.feature.bank.presentation.list.utils.toUi
import su.tease.project.design.icons.R as RIcons

class BankAccountsPage(
    store: Store<*>,
    private val loadBankAccountList: LoadBankAccountsAction,
    private val resourceProvider: ResourceProvider,
    private val dateProvider: DateProvider,
    private val cashBackPresetIconView: CashBackPresetIconView,
    private val bankPresetIconView: BankPresetIconView,
    private val cashBackInfoDialogView: CashBackInfoDialogView,
) : BasePageComponent<BankAccountsPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    init {
        dispatch(LoadBankAccountsActions.OnDateSelect(dateProvider.current().toCashBackDate()))
    }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(BankAccountsState::status)
        val date = selectAsState(BankAccountsState::date)
        val dates = selectAsState(BankAccountsState::dates)
        val list = selectAsState(BankAccountsState::list)
            .map { it.toUi(date.value, bankPresetIconView, cashBackPresetIconView, store) }

        LaunchedEffect(date.value) {
            if (date.value === defaultCashBackDate) return@LaunchedEffect
            dispatch(loadBankAccountList(date.value))
        }

        val isAddButtonVisible = remember {
            derivedStateOf {
                status.value == LoadingStatus.Success || list.value.isNotEmpty()
            }
        }

        val (isScrollTopButtonVisible, scrollDirection, nestedScrollConnection, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = { SaveCashBackPage(date = date.value).forward() },
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

        DFPage(
            title = stringResource(R.string.Bank_ListBankPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            showBackButton = selectAsState(hasPrevPageSelector).value,
            additionalTitleContent = {
                AnimatedVisibility(
                    visible = scrollDirection.value == ScrollDirection.BOTTOM,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    DFDropdownMenu(
                        selected = date.value,
                        items = dates.value,
                        onItemClick = { dispatch(LoadBankAccountsActions.OnDateSelect(it)) },
                        text = { dateProvider.toText(it.toMonthYear()) },
                        modifier = Modifier.padding(bottom = Theme.sizes.padding4),
                        background = Theme.colors.background2,
                        clip = RoundedCornerShape(Theme.sizes.round16)
                    )
                }
            }
        ) {
            val state = status.value
            when {
                state == LoadingStatus.Init -> BankAccountsPageInit(lazyListWrapper)
                state == LoadingStatus.Loading && list.value.isEmpty() -> BankAccountsPageLoading(lazyListWrapper)
                state == LoadingStatus.Failed -> BankAccountsPageFailed({ onTryAgain(date) })
                else -> BankAccountsPageSuccess(
                    list,
                    lazyListWrapper,
                    Modifier.nestedScroll(nestedScrollConnection)
                )
            }
        }

        cashBackInfoDialogView.Compose(
            info = selectAsState(BankAccountsState::shownCashBack),
            onHide = { dispatch(CashBackInfoDialogAction.OnHide) },
            dateProvider = dateProvider,
        )
    }

    private fun onTryAgain(date: State<CashBackDate>) =
        dispatch(loadBankAccountList(date.value)).unit()

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3

val hasPrevPageSelector = Selector<NavigationState, Boolean> {
    val page = root.feature.stack.prev?.value?.page // Prev page in current feature
        ?: root.app.stack.prev?.value?.page // Last page in prev feature
        ?: root.stack.prev?.value?.page // Last page in prev app
    page != null
}
