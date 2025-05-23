package su.tease.project.feature.cacheback.presentation.save.cacheback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.runIf
import su.tease.project.core.utils.ext.thenIf
import su.tease.project.design.component.controls.form.DFFormElement
import su.tease.project.design.component.controls.text.DFPlaceholder
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.presentation.save.cacheback.utls.FormFieldError
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

@Composable
fun SaveCacheBackCacheBackPresetSelect(
    cacheBackPreset: State<CacheBackPreset?>,
    enabled: State<Boolean>,
    onSelect: () -> Unit,
    error: State<FormFieldError?>,
    modifier: Modifier = Modifier
) {
    DFFormElement(
        label = stringResource(R.string.item_cache_back_preset_select_title),
        info = runIf(!enabled.value) {
            stringResource(R.string.item_cache_back_preset_select_info)
        },
        error = runIf(error.value == FormFieldError.REQUIRED_BUT_EMPTY) {
            stringResource(R.string.item_cache_back_preset_select_error)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.sizes.roundInfinity))
                .thenIf(enabled.value) { Modifier.clickable { onSelect() } }
                .background(Theme.colors.inputBackground)
                .fillMaxWidth()
                .height(Theme.sizes.size48)
                .padding(horizontal = Theme.sizes.padding10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            cacheBackPreset.value?.let {
                SaveCacheBackCacheBackPresetPreview(it)
            } ?: run {
                DFPlaceholder(
                    text = stringResource(R.string.item_cache_back_preset_select_placeholder),
                    modifier = Modifier.padding(
                        horizontal = Theme.sizes.padding6,
                        vertical = Theme.sizes.padding6,
                    )
                )
            }
        }
    }
}
