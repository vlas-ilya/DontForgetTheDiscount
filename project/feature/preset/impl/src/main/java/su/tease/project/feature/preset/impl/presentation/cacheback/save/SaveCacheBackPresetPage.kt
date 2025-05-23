package su.tease.project.feature.preset.impl.presentation.cacheback.save

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
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetAction
import su.tease.project.feature.preset.impl.presentation.cacheback.save.action.SaveCacheBackPresetActions
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackBankSelect
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackIconSelect
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackInfoEditText
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackMccCodeSelect
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackNameEditText
import su.tease.project.feature.preset.impl.presentation.cacheback.save.component.CacheBackSaveButton
import su.tease.project.feature.preset.impl.presentation.cacheback.save.reducer.SaveCacheBackPresetReducer
import su.tease.project.feature.preset.impl.presentation.cacheback.save.reducer.SaveCacheBackPresetState
import su.tease.project.feature.preset.impl.presentation.cacheback.save.utils.SaveCacheBackPresetForm
import su.tease.project.feature.preset.impl.presentation.icon.select.IconSelectPresetPage
import su.tease.project.feature.preset.impl.presentation.mcc.select.SelectMccCodePresetPage

class SaveCacheBackPresetPage(
    store: Store<*>,
    private val target: Target,
    private val saveCacheBackPresetAction: SaveCacheBackPresetAction,
) : BasePageComponent<SaveCacheBackPresetPage.Target>(store) {

    private val form = SaveCacheBackPresetForm(
        nameValue = target.cacheBackPreset?.name,
        infoValue = target.cacheBackPreset?.info,
        iconPresetValue = target.cacheBackPreset?.iconPreset,
        bankPresetValue = target.cacheBackPreset?.bankPreset,
        mccCodesValue = target.cacheBackPreset?.mccCodes,
        disabledBankPresetValue = target.bankPreset,
    )

    init {
        dispatch(SaveCacheBackPresetActions.OnInit(target.bankPreset, target.cacheBackPreset))
    }

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        RedirectState(selectAsState(SaveCacheBackPresetState::iconPreset), form.iconPreset)
        RedirectState(selectAsState(SaveCacheBackPresetState::bankPreset), form.bankPreset)
        RedirectStateNotNull(selectAsState(SaveCacheBackPresetState::mccCodes), form.mccCodes)

        val status = selectAsState(SaveCacheBackPresetState::status).value

        LaunchedEffect(status) {
            if (status == LoadingStatus.Success) {
                dispatch(SaveCacheBackPresetActions.OnInit())
                back()
            }
        }

        DFPage(
            title = stringResource(
                (target.cacheBackPreset == null).choose(
                    R.string.page_cache_back_preset_save_title_add,
                    R.string.page_cache_back_preset_save_title_edit,
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
                CacheBackBankSelect(
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
                    CacheBackIconSelect(
                        iconState = form.iconPreset,
                        onSelect = { selectIcon() },
                        error = form.ui { iconError },
                        modifier = Modifier.wrapContentWidth(),
                    )
                    Spacer(modifier = Modifier.width(Theme.sizes.padding8))
                    CacheBackNameEditText(
                        nameState = form.name,
                        onChange = { form.name.value = it },
                        error = form.ui { nameError },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                CacheBackInfoEditText(
                    infoState = form.info,
                    onChange = { form.info.value = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                CacheBackMccCodeSelect(
                    mccCodesState = form.mccCodes,
                    onSelect = { selectMccCode() }
                )
                Spacer(modifier = Modifier.weight(1F))
                CacheBackSaveButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { save(target.cacheBackPreset?.id) }
                )
            }
        }
    }

    private fun selectBank() {
        SelectBankPresetPage<SaveCacheBackPresetReducer>(form.bankPreset.value).forward()
    }

    private fun selectIcon() {
        IconSelectPresetPage<SaveCacheBackPresetReducer>(
            iconType = IconSelectPresetPage.IconType.CACHE_BACK_ICON,
            pageTitle = R.string.page_select_cache_back_item_icon_title,
            selected = form.iconPreset.value
        ).forward()
    }

    private fun selectMccCode() {
        SelectMccCodePresetPage<SaveCacheBackPresetReducer>(form.mccCodes.value).forward()
    }

    private fun save(cacheBackPresetId: String?) {
        form.makeResult(cacheBackPresetId)?.let { dispatch(saveCacheBackPresetAction(it)) }
    }

    @Parcelize
    data class Target(
        val bankPreset: BankPreset? = null,
        val cacheBackPreset: CacheBackPreset? = null
    ) : NavigationTarget.Page

    companion object {
        operator fun invoke(
            bankPreset: BankPreset? = null,
            cacheBackPreset: CacheBackPreset? = null
        ) = Target(bankPreset, cacheBackPreset)
    }
}
