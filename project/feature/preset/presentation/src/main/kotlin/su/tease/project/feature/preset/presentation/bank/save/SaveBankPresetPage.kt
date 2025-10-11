package su.tease.project.feature.preset.presentation.bank.save

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.component.IconSelect
import su.tease.project.feature.preset.presentation.bank.component.NameEditText
import su.tease.project.feature.preset.presentation.bank.component.SaveButton
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions
import su.tease.project.feature.preset.presentation.bank.save.reducer.SaveBankPresetReducer
import su.tease.project.feature.preset.presentation.bank.save.reducer.SaveBankPresetState
import su.tease.project.feature.preset.presentation.bank.save.utils.SaveBankPresetForm
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage

class SaveBankPresetPage(
    store: Store<*>,
    private val target: Target,
    private val saveBankPresetAction: SaveBankPresetAction,
) : BasePageComponent<SaveBankPresetPage.Target>(store) {

    private val form = SaveBankPresetForm(target.bankPreset)

    init {
        dispatch(SaveBankPresetActions.OnInit(target.bankPreset))
    }

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        RedirectState(selectAsState(SaveBankPresetState::icon), form.iconPreset)
        RedirectState(selectAsState(SaveBankPresetState::error), form.error)

        val status = selectAsState(SaveBankPresetState::status).value

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveBankPresetActions.OnInit())
                form.clean()
                back()
            }
        }

        DFPage(
            title = (target.bankPreset == null).choose(
                stringResource(R.string.Presets_SaveBankPage_Add_Title),
                stringResource(R.string.Presets_SaveBankPage_Edit_Title),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    IconSelect(
                        iconState = form.iconPreset,
                        onSelect = { selectIcon(R.string.Presets_SaveBankPage_IconSelect_Title) },
                        error = form.ui { iconError },
                        modifier = Modifier.wrapContentWidth(),
                    )
                    Spacer(modifier = Modifier.width(Theme.sizes.padding8))
                    NameEditText(
                        nameState = form.name,
                        focusRequester = focusRequester,
                        onChange = { form.setName(it) },
                        error = form.ui { nameError },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                SaveButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { save() }
                )
            }
        }
    }

    private fun selectIcon(@StringRes title: Int) {
        SelectIconPresetPage<SaveBankPresetReducer>(
            iconType = SelectIconPresetPage.IconType.BANK_ICON,
            pageTitle = title,
            selected = form.iconPreset.value
        ).forward()
    }

    private fun save() {
        form.makeResult()?.let {
            dispatch(saveBankPresetAction(it))
        }
    }

    @Parcelize
    data class Target(val bankPreset: BankPreset?) : NavigationTarget.Page

    companion object {
        operator fun invoke(bankPreset: BankPreset? = null) = Target(bankPreset = bankPreset)
    }
}
