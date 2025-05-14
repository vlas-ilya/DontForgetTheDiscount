package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.core.mvi.component.component.impl.NavigationComponent
import su.tease.core.mvi.component.component.impl.NavigationComponentImpl
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.presentation.component.CacheBackPresetIcon
import su.tease.project.feature.cacheback.presentation.component.CacheBackPresetIconSize
import su.tease.project.feature.cacheback.presentation.list.reducer.CacheBackInfoDialogAction
import su.tease.project.feature.cacheback.presentation.save.SaveCacheBackFeature
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.design.icons.R as RIcons

data class CacheBackItem(
    private val bankAccount: BankAccount,
    private val cacheBack: CacheBack,
    private val store: Store<*>,
) : LazyListItem, NavigationComponent by NavigationComponentImpl(store) {

    override val key = CACHE_BACK + cacheBack.id

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .clickable {
                    SaveCacheBackFeature(
                        SaveCacheBackRequest(
                            id = cacheBack.id,
                            bankAccount = bankAccount,
                            cacheBackPreset = cacheBack.cacheBackPreset,
                            size = cacheBack.size,
                            date = cacheBack.date,
                        )
                    ).forward()
                }
                .padding(horizontal = Theme.sizes.padding8)
                .padding(start = Theme.sizes.padding20),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CacheBackPresetIcon(
                cacheBackPreset = cacheBack.cacheBackPreset,
                size = CacheBackPresetIconSize.SMALL,
            )
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            Row(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DFText(
                    text = stringResource(
                        R.string.item_cache_back_in_list_label_percent,
                        cacheBack.size.toString(),
                    ),
                    style = Theme.fonts.monospace,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFText(
                    text = cacheBack.cacheBackPreset.name,
                    style = Theme.fonts.text,
                )
            }
            runIf(
                cacheBack.cacheBackPreset.info.isNotBlank() ||
                    cacheBack.cacheBackPreset.mccCodes.isNotEmpty()
            ) {
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFIconButton(
                    icon = RIcons.drawable.comment_info,
                    size = DFIconButtonSize.S,
                    onClick = {
                        store.dispatcher.dispatch(
                            CacheBackInfoDialogAction.OnShow(bankAccount to cacheBack)
                        )
                    }
                )
            }
        }
    }
}

private const val CACHE_BACK = "CACHE_BACK"
