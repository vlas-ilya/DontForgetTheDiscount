package su.tease.project.feature.cacheback.presentation.select.bank

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
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.usecase.AddBankAction
import su.tease.project.feature.cacheback.domain.usecase.AddBankUseCase
import su.tease.project.feature.cacheback.presentation.reducer.AddBankReducer
import su.tease.project.feature.cacheback.presentation.reducer.AddBankState
import su.tease.project.feature.cacheback.presentation.select.bank.component.IconSelect
import su.tease.project.feature.cacheback.presentation.select.bank.component.NameEditText
import su.tease.project.feature.cacheback.presentation.select.bank.component.SaveButton
import su.tease.project.feature.cacheback.presentation.select.bank.utils.AddBankForm
import su.tease.project.feature.cacheback.presentation.select.icon.IconSelectPage

class AddBankPage(
    store: Store<*>,
    private val addBankUseCase: AddBankUseCase,
) : BasePageComponent(store) {

    private val form = AddBankForm()

    init {
        dispatch(AddBankAction.OnInit)
    }

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(titleRes = R.string.page_add_bank_title) }

        RedirectState(selectAsState(AddBankState::icon), form.icon)
        RedirectState(selectAsState(AddBankState::error), form.error)

        val status = selectAsState(AddBankState::status).value

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(AddBankAction.OnInit)
                back()
            }
        }

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
                    iconState = form.icon,
                    onSelect = { selectIcon(R.string.page_add_bank_icon_title) },
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

    private fun selectIcon(@StringRes title: Int) {
        forward(
            IconSelectPage<AddBankReducer>(
                iconType = IconSelectPage.IconType.BANK_ICON,
                pageTitle = title,
                selected = form.icon.value
            )
        )
    }

    private fun save() {
        form.makeResult()?.let {
            dispatch(addBankUseCase(it))
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}
