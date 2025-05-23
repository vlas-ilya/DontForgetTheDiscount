package su.tease.project.feature.preset.impl.presentation.cacheback.select.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.api.presentation.component.BankPresetIcon
import su.tease.project.feature.preset.api.presentation.component.BankPresetIconSize
import su.tease.project.feature.preset.api.presentation.component.CacheBackPresetIcon
import su.tease.project.feature.preset.api.presentation.component.CacheBackPresetIconSize
import su.tease.project.feature.preset.api.presentation.component.MccCodeItem

@Composable
fun CacheBackInfoDialogContent(
    data: Pair<BankPreset, CacheBackPreset>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (bankPreset, cacheBackPreset) = data
    Column(
        modifier = modifier.padding(horizontal = Theme.sizes.padding8)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CacheBackPresetIcon(
                cacheBackPreset = cacheBackPreset,
                size = CacheBackPresetIconSize.DEFAULT,
            )

            Spacer(Modifier.width(Theme.sizes.padding4))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BankPresetIcon(
                    bankPreset = bankPreset,
                    size = BankPresetIconSize.DEFAULT,
                )
                DFText(
                    text = bankPreset.name,
                    style = Theme.fonts.placeholder,
                    modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
                )
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFTextH1(
            text = cacheBackPreset.name,
            maxLines = 1
        )

        cacheBackPreset.info.takeIf { it.isNotBlank() }?.let {
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFText(
                text = it,
                style = Theme.fonts.placeholder,
            )
        }

        cacheBackPreset.mccCodes.takeIf { it.isNotEmpty() }?.let { items ->
            Spacer(Modifier.height(Theme.sizes.padding12))
            DFFormElement(
                label = stringResource(R.string.dialog_cache_back_preset_codes_label_other),
                noError = true,
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Theme.sizes.size56)) {
                    items(
                        items = items,
                        key = { it.info }
                    ) {
                        MccCodeItem(
                            code = it.code,
                            onClick = { }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(Theme.sizes.padding12))

        DFButton(
            label = stringResource(R.string.dialog_cache_back_preset_info_button_close),
            onClick = onClick
        )

        Spacer(Modifier.height(Theme.sizes.padding12))
    }
}
