package su.tease.project.feature.bank.presentation.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.list.LazyList
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.component.BankAccountsItem
import su.tease.project.feature.bank.presentation.dependencies.view.BankPresetIconView
import su.tease.project.feature.bank.presentation.info.list.utils.toUi
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage
import su.tease.project.feature.bank.presentation.select.reducer.SelectBankAccountPageReducer
import su.tease.project.feature.bank.presentation.select.reducer.SelectBankAccountState
import su.tease.project.design.icons.R as RIcons

class SelectBankAccountPage(
    store: Store<*>,
    private val target: Target,
    private val interceptor: BankAccountInterceptor,
    private val resourceProvider: ResourceProvider,
    private val bankPresetIconView: BankPresetIconView,
) : BasePageComponent<SelectBankAccountPage.Target>(store) {

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    init {
        dispatch(OnInit)
    }

    @Composable
    override fun invoke() {
        val savedBankAccount = selectAsState(SelectBankAccountState::savedBankAccount).value
        val list = memoize { interceptor.list() }
            .map { it?.toUi(bankPresetIconView, store, ::onBankAccountClick) }

        LaunchedEffect(savedBankAccount) {
            if (savedBankAccount != null) {
                dispatch(OnSelectAction(target.target, savedBankAccount))
                back()
            }
        }

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
            }
        }

        val (_, _, resetScroll) = scrollDirectionState

        val scope = rememberCoroutineScope()
        val scrollUp = rememberCallback(resetScroll, lazyListState) {
            scope.launch {
                resetScroll()
                lazyListState.animateScrollToItem(0)
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveBankAccountPage<SelectBankAccountPageReducer>().forward() }
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
                SelectBankAccountShimmer()
                return@DFPage
            }

            LazyList(
                count = banks.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = banks::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                lazyListState = lazyListState,
            )
        }
    }

    private fun onBankAccountClick(bankAccount: BankAccount) {
        dispatch(OnSelectAction(target.target, bankAccount))
        back()
    }

    @Composable
    private fun SelectBankAccountShimmer() {
        Shimmer {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding4))
                repeat(SHIMMER_ITEM_COUNT) { BankAccountsItem.Shimmer() }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
    ) : NavigationTarget.Page

    @Parcelize
    data object OnInit : PlainAction

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankAccount?,
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke() = Target(T::class.java.name)
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3