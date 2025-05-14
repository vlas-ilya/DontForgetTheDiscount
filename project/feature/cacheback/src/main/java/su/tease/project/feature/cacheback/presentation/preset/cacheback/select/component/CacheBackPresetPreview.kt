package su.tease.project.feature.cacheback.presentation.preset.cacheback.select.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.presentation.component.CacheBackPresetIcon
import su.tease.project.feature.cacheback.presentation.component.CacheBackPresetIconSize
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.CacheBackPresetInfoDialogAction
import su.tease.project.design.icons.R as RIcons

@Composable
fun CacheBackPresetPreview(
    store: Store<*>,
    cacheBackPreset: CacheBackPreset,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CacheBackPresetIcon(
            cacheBackPreset = cacheBackPreset,
            size = CacheBackPresetIconSize.SMALL,
        )

        Spacer(modifier = Modifier.width(Theme.sizes.padding4))

        DFText(
            text = cacheBackPreset.name,
            style = Theme.fonts.text,
            modifier = Modifier
                .weight(1F)
                .fillMaxSize(),
        )
        runIf(
            cacheBackPreset.info.isNotBlank() ||
                cacheBackPreset.mccCodes.isNotEmpty()
        ) {
            Spacer(modifier = Modifier.width(Theme.sizes.padding4))
            DFIconButton(
                icon = RIcons.drawable.comment_info,
                size = DFIconButtonSize.S,
                onClick = {
                    store.dispatcher.dispatch(
                        CacheBackPresetInfoDialogAction.OnShow(
                            cacheBackPreset.bankPreset to cacheBackPreset
                        )
                    )
                }
            )
        }
    }
}
