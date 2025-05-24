package su.tease.project.feature.preset.impl.presentation.cashback.save

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.core.utils.ext.RedirectStateNotNull
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetActions
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackBankSelect
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackIconSelect
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackInfoEditText
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackMccCodeSelect
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackNameEditText
import su.tease.project.feature.preset.impl.presentation.cashback.save.component.CashBackSaveButton
import su.tease.project.feature.preset.impl.presentation.cashback.save.reducer.SaveCashBackPresetReducer
import su.tease.project.feature.preset.impl.presentation.cashback.save.reducer.SaveCashBackPresetState
import su.tease.project.feature.preset.impl.presentation.cashback.save.utils.SaveCashBackPresetForm
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.SelectMccCodePresetPage

class SaveCashBackPresetPage(
    store: Store<*>,
    private val target: Target,
    private val saveCashBackPresetAction: SaveCashBackPresetAction,
) : BasePageComponent<SaveCashBackPresetPage.Target>(store) {

    private val form = SaveCashBackPresetForm(
        nameValue = target.cashBackPreset?.name,
        infoValue = target.cashBackPreset?.info,
        iconPresetValue = target.cashBackPreset?.iconPreset,
        bankPresetValue = target.cashBackPreset?.bankPreset,
        mccCodesValue = target.cashBackPreset?.mccCodes,
        disabledBankPresetValue = target.bankPreset,
    )

    init {
        dispatch(SaveCashBackPresetActions.OnInit(target.bankPreset, target.cashBackPreset))
    }

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        RedirectState(selectAsState(SaveCashBackPresetState::iconPreset), form.iconPreset)
        RedirectState(selectAsState(SaveCashBackPresetState::bankPreset), form.bankPreset)
        RedirectStateNotNull(selectAsState(SaveCashBackPresetState::mccCodes), form.mccCodes)

        val status = selectAsState(SaveCashBackPresetState::status).value

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveCashBackPresetActions.OnInit())
                back()
            }
        }

        DFPage(
            title = stringResource(
                (target.cashBackPreset == null).choose(
                    R.string.page_cash_back_preset_save_title_add,
                    R.string.page_cash_back_preset_save_title_edit,
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
                    .padding(WindowInsets.ime.asPaddingValues())
            ) {
                CashBackBankSelect(
                    bankState = form.bankPreset,
                    enabled = form.bankPresetEnabled,
                    onSelect = { selectBank() },
                    error = form.ui { bankPresetError },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    CashBackIconSelect(
                        iconState = form.iconPreset,
                        onSelect = { selectIcon() },
                        error = form.ui { iconError },
                        modifier = Modifier.wrapContentWidth(),
                    )
                    Spacer(modifier = Modifier.width(Theme.sizes.padding8))
                    CashBackNameEditText(
                        nameState = form.name,
                        onChange = { form.name.value = it },
                        error = form.ui { nameError },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                CashBackInfoEditText(
                    infoState = form.info,
                    onChange = { form.info.value = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                CashBackMccCodeSelect(
                    mccCodesState = form.mccCodes,
                    onSelect = { selectMccCode() }
                )
                Spacer(modifier = Modifier.weight(1F))
                CashBackSaveButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { save(target.cashBackPreset?.id) }
                )
            }
        }
    }

    private fun selectBank() {
        SelectBankPresetPage<SaveCashBackPresetReducer>(form.bankPreset.value).forward()
    }

    private fun selectIcon() {
        IconSelectPresetPage<SaveCashBackPresetReducer>(
            iconType = IconSelectPresetPage.IconType.CASH_BACK_ICON,
            pageTitle = R.string.page_select_cash_back_item_icon_title,
            selected = form.iconPreset.value
        ).forward()
    }

    private fun selectMccCode() {
        SelectMccCodePresetPage<SaveCashBackPresetReducer>(form.mccCodes.value).forward()
    }

    private fun save(cashBackPresetId: String?) {
        form.makeResult(cashBackPresetId)?.let { dispatch(saveCashBackPresetAction(it)) }
    }

    @Parcelize
    data class Target(
        val bankPreset: BankPreset? = null,
        val cashBackPreset: CashBackPreset? = null
    ) : NavigationTarget.Page

    companion object {
        operator fun invoke(
            bankPreset: BankPreset? = null,
            cashBackPreset: CashBackPreset? = null
        ) = Target(bankPreset, cashBackPreset)
    }
}
