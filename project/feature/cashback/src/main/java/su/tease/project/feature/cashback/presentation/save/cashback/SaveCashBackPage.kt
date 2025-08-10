@file:Suppress("EmptyFunctionBlock")

package su.tease.project.feature.cashback.presentation.save.cashback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.core.utils.ext.RedirectStateNotNull
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.defaultCashBackDate
import su.tease.project.feature.cashback.domain.mapper.toCashBackDate
import su.tease.project.feature.cashback.presentation.save.bank.select.SelectBankAccountPage
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackBankAccountSelect
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackCashBackPresetSelect
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackDateSelect
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackSaveAndAddMoreButton
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackSaveButton
import su.tease.project.feature.cashback.presentation.save.cashback.component.SaveCashBackSizeSelect
import su.tease.project.feature.cashback.presentation.save.cashback.reducer.SaveCashBackReducer
import su.tease.project.feature.cashback.presentation.save.cashback.reducer.SaveCashBackState
import su.tease.project.feature.cashback.presentation.save.cashback.utls.SaveCashBackForm
import su.tease.project.feature.preset.api.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackActions as Save

class SaveCashBackPage(
    store: Store<*>,
    private val target: Target,
    private val dateProvider: DateProvider,
    private val saveCashBackAction: SaveCashBackAction,
) : BasePageComponent<SaveCashBackPage.Target>(store) {

    private val dateValue = target.saveCashBackRequest?.date
        .takeIf { it !== defaultCashBackDate }
        ?: dateProvider.current().toCashBackDate()

    private val dates = dateProvider.currentAndNext().mapPersistent { it.toCashBackDate() }

    private val form = SaveCashBackForm(
        bankAccountValue = target.saveCashBackRequest?.bankAccount,
        cashBackPresetValue = target.saveCashBackRequest?.cashBackPreset,
        sizeValue = target.saveCashBackRequest?.size,
        dateValue = dateValue,
    )

    init {
        dispatch(Save.OnInit(target.saveCashBackRequest))
    }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(SaveCashBackState::status).value

        LaunchedEffect(status) {
            if (status != LoadingStatus.Success) return@LaunchedEffect

            if (form.addMore.value.not()) {
                dispatch(Save.OnInit(target.saveCashBackRequest))
                back()
            } else {
                val bank = form.bankAccount.value
                val date = form.date.value
                form.clean()
                dispatch(Save.OnInit(SaveCashBackRequest(bankAccount = bank, date = date)))
            }
        }

        val isCreatingNew = target.saveCashBackRequest?.id == null

        DFPage(
            title = stringResource(
                (isCreatingNew).choose(
                    R.string.page_save_cash_back_title_add,
                    R.string.page_save_cash_back_title_edit,
                )
            ),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            when (status) {
                LoadingStatus.Success -> return@DFPage
                LoadingStatus.Init -> CashBackAddPageForm(isCreatingNew)
                LoadingStatus.Loading -> CashBackAddPageLoading()
                LoadingStatus.Failed -> CashBackAddPageFailed()
            }
        }
    }

    @Composable
    private fun CashBackAddPageForm(
        isCreatingNew: Boolean
    ) {
        RedirectState(selectAsState(SaveCashBackState::bankAccount), form.bankAccount)
        RedirectState(selectAsState(SaveCashBackState::cashBackPreset), form.cashBackPreset)
        RedirectStateNotNull(selectAsState(SaveCashBackState::date), form.date)

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.ime.asPaddingValues())
        ) {
            SaveCashBackDateSelect(
                dateState = form.date,
                dates = dates,
                onSelect = { dispatch(Save.OnSetDate(it)) },
                modifier = Modifier.fillMaxWidth(),
                dateProvider = dateProvider,
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            SaveCashBackBankAccountSelect(
                bankState = form.bankAccount,
                onSelect = { selectBankAccount() },
                error = form.ui { bankAccountError },
                disabled = !isCreatingNew,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            SaveCashBackCashBackPresetSelect(
                cashBackPreset = form.cashBackPreset,
                enabled = form.cashBackPresetEnabled,
                onSelect = { selectCashBackPreset() },
                error = form.ui { cashBackPresetError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            SaveCashBackSizeSelect(
                sizeState = form.size,
                onChange = { form.size.value = it },
                error = form.ui { sizeError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            SaveCashBackSaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { save(target.saveCashBackRequest?.id) }
            )
            runIf(target.saveCashBackRequest?.id == null) {
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                SaveCashBackSaveAndAddMoreButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { saveAndAddMore() }
                )
            }
        }
    }

    private fun selectBankAccount() {
        SelectBankAccountPage<SaveCashBackReducer>().forward()
    }

    private fun selectCashBackPreset() {
        val bankPreset = form.bankAccount.value?.bankPreset ?: return
        SelectCashBackPresetPage<SaveCashBackReducer>(bankPreset).forward()
    }

    private fun save(cashBackId: String?) {
        form.makeResult(cashBackId)?.let {
            form.addMore.value = false
            dispatch(saveCashBackAction(it))
        }
    }

    private fun saveAndAddMore() {
        form.makeResult()?.let {
            form.addMore.value = true
            dispatch(saveCashBackAction(it))
        }
    }

    @Composable
    private fun CashBackAddPageFailed() {
    }

    @Composable
    private fun CashBackAddPageLoading() {
    }

    @Parcelize
    data class Target(val saveCashBackRequest: SaveCashBackRequest?) : NavigationTarget.Page

    companion object {
        operator fun invoke(
            saveCashBackRequest: SaveCashBackRequest?
        ) = Target(saveCashBackRequest)
    }
}
