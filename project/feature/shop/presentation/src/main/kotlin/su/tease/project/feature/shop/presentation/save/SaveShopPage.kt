package su.tease.project.feature.shop.presentation.save

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
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.dependencies.navigation.SelectShopPresetPage
import su.tease.project.feature.shop.presentation.dependencies.view.ShopPresetIconView
import su.tease.project.feature.shop.presentation.save.action.SaveShopAction
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions
import su.tease.project.feature.shop.presentation.save.component.SaveShopPageShopPresetSelect
import su.tease.project.feature.shop.presentation.save.component.SaveShopPageNameEditText
import su.tease.project.feature.shop.presentation.save.component.SaveShopPageSaveButton
import su.tease.project.feature.shop.presentation.save.reducer.SaveShopState
import su.tease.project.feature.shop.presentation.save.utils.SaveShopForm

class SaveShopPage(
    store: Store<*>,
    private val target: Target,
    private val saveShopAction: SaveShopAction,
    private val shopPresetIconView: ShopPresetIconView,
) : BasePageComponent<SaveShopPage.Target>(store) {

    private val form = SaveShopForm(
        cashBackOwnerPresetValue = target.shop?.preset,
        customNameValue = target.shop?.customName
            ?: target.shop?.preset?.name
            ?: "",
    )

    init {
        dispatch(SaveShopActions.OnInit(target.shop))
    }

    @Composable
    override fun invoke() {
        RedirectStateNotNull(selectAsState(SaveShopState::ownerPreset), form.shopPreset)
        RedirectStateNotNull(selectAsState(SaveShopState::customName), form.customName)

        val shopId = selectAsState(SaveShopState::id).value
        val status = selectAsState(SaveShopState::status).value

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveShopActions.OnInit())
                form.clean()
                back()
            }
        }

        DFPage(
            title = stringResource(
                (target.shop == null).choose(
                    R.string.Shop_SaveShopPage_Add_Title,
                    R.string.Shop_SaveShopPage_Save_Title,
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
                SaveShopPageShopPresetSelect(
                    shopState = form.shopPreset,
                    onSelect = { SelectShopPresetPage(form.shopPreset.value).forward() },
                    error = form.ui { shopPresetError },
                    shopPresetIconView = shopPresetIconView,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                SaveShopPageNameEditText(
                    nameState = form.customName,
                    onChange = { form.customName.value = it },
                    error = form.ui { customNameError },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.weight(1F))
                SaveShopPageSaveButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { save(shopId ?: "") }
                )
            }
        }
    }

    private fun save(shopId: String) {
        form.makeResult(shopId)?.let {
            dispatch(saveShopAction(it))
        }
    }

    @Parcelize
    data class Target(
        val shop: Shop? = null
    ) : NavigationTarget.Page

    companion object {
        operator fun invoke(shop: Shop? = null) = Target(shop)
    }
}
