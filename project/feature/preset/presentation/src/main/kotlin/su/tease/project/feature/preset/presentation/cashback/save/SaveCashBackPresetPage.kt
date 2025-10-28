package su.tease.project.feature.preset.presentation.cashback.save

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
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPresetType
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPresetType.BANK
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPresetType.SHOP
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.entity.cashBackOwnerPresetType
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetActions
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackIconSelect
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackInfoEditText
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackMccCodeSelect
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackNameEditText
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackOwnerPresetSelect
import su.tease.project.feature.preset.presentation.cashback.save.component.CashBackSaveButton
import su.tease.project.feature.preset.presentation.cashback.save.reducer.SaveCashBackPresetReducer
import su.tease.project.feature.preset.presentation.cashback.save.reducer.SaveCashBackPresetState
import su.tease.project.feature.preset.presentation.cashback.save.utils.SaveCashBackPresetForm
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage

class SaveCashBackPresetPage(
    store: Store<*>,
    private val target: Target,
    private val saveCashBackPresetAction: SaveCashBackPresetAction,
) : BasePageComponent<SaveCashBackPresetPage.Target>(store) {

    private val form = SaveCashBackPresetForm(
        nameValue = target.preset?.name,
        infoValue = target.preset?.info,
        iconPresetValue = target.preset?.iconPreset,
        cashBackOwnerPreset = target.preset?.cashBackOwnerPreset,
        mccCodesValue = target.preset?.mccCodes,
        disabledCashBackOwnerPresetValue = target.ownerPreset,
    )

    init {
        dispatch(
            SaveCashBackPresetActions.OnInit(
                target.ownerPreset,
                target.preset
            )
        )
    }

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        RedirectState(selectAsState(SaveCashBackPresetState::iconPreset), form.iconPreset)
        RedirectState(
            selectAsState(SaveCashBackPresetState::cashBackOwnerPreset),
            form.cashBackOwnerPreset
        )
        RedirectStateNotNull(selectAsState(SaveCashBackPresetState::mccCodes), form.mccCodes)

        val status = selectAsState(SaveCashBackPresetState::status).value

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveCashBackPresetActions.OnInit())
                form.clean()
                back()
            }
        }

        DFPage(
            title = stringResource(
                (target.preset == null).choose(
                    R.string.Presets_SaveCashBackPresetPage_Add_Title,
                    R.string.Presets_SaveCashBackPresetPage_Edit_Title,
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
                CashBackOwnerPresetSelect(
                    cashBackOwnerPreset = form.cashBackOwnerPreset,
                    enabled = form.cashBackOwnerPresetEnabled,
                    onSelect = { selectBank() },
                    error = form.ui { cashBackOwnerPresetError },
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
                    onSubmit = { save(target.preset?.id) }
                )
            }
        }
    }

    private fun selectBank() = form.cashBackOwnerPreset.value.let {
        when (target.ownerType) {
            BANK -> SelectBankPresetPage<SaveCashBackPresetReducer>(it as BankPreset?)
            SHOP -> SelectShopPresetPage<SaveCashBackPresetReducer>(it as ShopPreset?)
        }.forward()
    }

    private fun selectIcon() {
        SelectIconPresetPage<SaveCashBackPresetReducer>(
            iconType = IconType.CASH_BACK_ICON,
            pageTitle = R.string.Presets_SaveCashBackPresetPage_IconSelect_PageTitle,
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
        val ownerPreset: CashBackOwnerPreset? = null,
        val ownerType: CashBackOwnerPresetType,
        val preset: CashBackPreset? = null
    ) : NavigationTarget.Page

    companion object {
        operator fun <T : CashBackOwnerPreset> invoke(
            ownerPreset: T,
            preset: CashBackPreset? = null
        ) = Target(ownerPreset, ownerPreset::class.cashBackOwnerPresetType, preset)
    }
}
