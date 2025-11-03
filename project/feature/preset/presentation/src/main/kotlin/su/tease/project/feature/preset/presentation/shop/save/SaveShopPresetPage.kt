package su.tease.project.feature.preset.presentation.shop.save

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
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.shop.component.IconSelect
import su.tease.project.feature.preset.presentation.shop.component.NameEditText
import su.tease.project.feature.preset.presentation.shop.component.SaveButton
import su.tease.project.feature.preset.presentation.shop.save.action.ExternalSaveShopPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetAction
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SelectShopIconAction
import su.tease.project.feature.preset.presentation.shop.save.reducer.SaveShopPresetState
import su.tease.project.feature.preset.presentation.shop.save.utils.SaveShopPresetForm

class SaveShopPresetPage(
    store: Store<*>,
    private val target: Target,
    private val saveShopPresetAction: SaveShopPresetAction,
    private val selectShopIconAction: SelectShopIconAction,
) : BasePageComponent<SaveShopPresetPage.Target>(store) {

    private val form = SaveShopPresetForm(target.shopPreset)

    init {
        dispatch(SaveShopPresetActions.OnInit(target.shopPreset))
    }

    override fun onFinish() {
        dispatch(ExternalSaveShopPresetActions.OnFinish)
    }

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        RedirectState(selectAsState(SaveShopPresetState::icon), form.iconPreset)
        RedirectState(selectAsState(SaveShopPresetState::error), form.error)

        val status = selectAsState(SaveShopPresetState::status).value

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveShopPresetActions.OnInit())
                form.clean()
            }
        }

        DFPage(
            title = (target.shopPreset == null).choose(
                stringResource(R.string.Presets_SaveShopPage_Add_Title),
                stringResource(R.string.Presets_SaveShopPage_Edit_Title),
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
                        onSelect = { dispatch(selectShopIconAction(form.iconPreset.value)) },
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

    private fun save() {
        form.makeResult()?.let { dispatch(saveShopPresetAction(it)) }
    }

    @Parcelize
    data class Target(val shopPreset: ShopPreset?) : NavigationTarget.Page

    companion object {
        operator fun invoke(shopPreset: ShopPreset? = null) = Target(shopPreset = shopPreset)
    }
}
