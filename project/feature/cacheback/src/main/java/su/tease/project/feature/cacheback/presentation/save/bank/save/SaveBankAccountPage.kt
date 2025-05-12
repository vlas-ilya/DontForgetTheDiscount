package su.tease.project.feature.cacheback.presentation.save.bank.save

import androidx.annotation.StringRes
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
import su.tease.project.core.utils.ext.RedirectStateNotNull
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.presentation.preset.bank.select.SelectBankPresetPage
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.SaveBankAccountAction
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.SaveBankAccountActions
import su.tease.project.feature.cacheback.presentation.save.bank.save.component.BankNameEditText
import su.tease.project.feature.cacheback.presentation.save.bank.save.component.BankSelect
import su.tease.project.feature.cacheback.presentation.save.bank.save.component.SaveButton
import su.tease.project.feature.cacheback.presentation.save.bank.save.reducer.SaveBankAccountReducer
import su.tease.project.feature.cacheback.presentation.save.bank.save.reducer.SaveBankAccountState
import su.tease.project.feature.cacheback.presentation.save.bank.save.utils.SaveBankAccountForm

class SaveBankAccountPage(
    store: Store<*>,
    private val target: Target,
    private val saveBankAccountAction: SaveBankAccountAction,
) : BasePageComponent<SaveBankAccountPage.Target>(store) {

    private val form = SaveBankAccountForm(
        bankPresetValue = target.bankAccount?.bankPreset,
        customNameValue = target.bankAccount?.customName
            ?: target.bankAccount?.bankPreset?.name
            ?: "",
    )

    init {
        dispatch(SaveBankAccountActions.OnInit(target.bankAccount))
    }

    @Composable
    override fun invoke() {
        RedirectStateNotNull(selectAsState(SaveBankAccountState::bankPreset), form.bankPreset)
        RedirectStateNotNull(selectAsState(SaveBankAccountState::customName), form.customName)

        val bankAccountId = selectAsState(SaveBankAccountState::id)
        val status = selectAsState(SaveBankAccountState::status).value

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveBankAccountActions.OnInit())
                back()
            }
        }

        DFPage(
            title = stringResource(
                (target.bankAccount == null).choose(
                    R.string.page_save_bank_account_title_add,
                    R.string.page_save_bank_account_title_edit,
                )
            ),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            Column(
                modifier = Modifier
                    .padding(Theme.sizes.padding8)
                    .padding(top = Theme.sizes.padding6)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(WindowInsets.ime.asPaddingValues())
            ) {
                BankSelect(
                    bankState = form.bankPreset,
                    onSelect = { selectBank(R.string.page_select_cache_back_bank_title) },
                    error = form.ui { bankPresetError },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                BankNameEditText(
                    nameState = form.customName,
                    onChange = { form.customName.value = it },
                    error = form.ui { customNameError },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.weight(1F))
                SaveButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { save(bankAccountId.value ?: "") }
                )
            }
        }
    }

    private fun selectBank(@StringRes title: Int) {
        SelectBankPresetPage<SaveBankAccountReducer>(
            pageTitle = title,
            selected = form.bankPreset.value
        ).forward()
    }

    private fun save(bankAccountId: String) {
        form.makeResult(bankAccountId)?.let {
            dispatch(saveBankAccountAction(it))
        }
    }

    @Parcelize
    data class Target(
        val bankAccount: BankAccount? = null
    ) : NavigationTarget.Page

    companion object {
        operator fun invoke(bankAccount: BankAccount? = null) = Target(bankAccount)
    }
}
