package su.tease.project.feature.preset.presentation.mcc.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.choose
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.preset.domain.entity.MccCodePreset

data class MccCodePresetPreview(
    private val mccCodePreset: MccCodePreset,
    private val store: Store<*>,
    private val onClick: ((MccCodePreset) -> Unit)? = null,
) : LazyListItem {

    override val key = MCC_CODE_PRESET + mccCodePreset.id

    @Composable
    override fun LazyItemScope.Compose() {
        val isExpanded = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillParentMaxWidth()
                .padding(horizontal = Theme.sizes.padding8)
                .clickable {
                    isExpanded.value = !isExpanded.value
                    onClick?.invoke(mccCodePreset)
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                DFText(
                    text = mccCodePreset.code,
                    style = Theme.fonts.monospace,
                    color = Theme.colors.inputText,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(end = Theme.sizes.padding2)
                        .padding(Theme.sizes.padding2)
                        .clip(RoundedCornerShape(Theme.sizes.round4))
                        .background(Theme.colors.inputBackground)
                        .padding(Theme.sizes.padding4)
                )
                DFText(
                    text = mccCodePreset.name,
                    style = Theme.fonts.text,
                    color = Theme.colors.text,
                    maxLines = 1,
                )
            }
            if (mccCodePreset.info.isNotBlank()) {
                DFText(
                    text = mccCodePreset.info,
                    style = Theme.fonts.text,
                    color = Theme.colors.info,
                    maxLines = isExpanded.value.choose(
                        Int.MAX_VALUE,
                        MCC_CODE_INFO_MAX_LINES,
                    ),
                )
            }
        }
    }

    data class Shimmer(
        private val index: Int,
    ) : LazyListItem {

        override val key: String = "${MCC_CODE_PRESET}_SHIMMER_$index"

        @Composable
        override fun LazyItemScope.Compose() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.sizes.padding8)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .padding(end = Theme.sizes.padding2)
                            .padding(Theme.sizes.padding2)
                            .clip(RoundedCornerShape(Theme.sizes.round4))
                            .background(Theme.colors.shimmer)
                            .size(47.dp, 27.dp)
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Theme.sizes.round4))
                            .background(Theme.colors.shimmer)
                            .height(26.dp)
                            .weight(1F)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Theme.sizes.round4))
                        .background(Theme.colors.shimmer)
                        .height(Theme.sizes.size60)
                        .fillMaxWidth()
                )
            }
        }
    }

}


private const val MCC_CODE_PRESET = "MCC_CODE_PRESET"
private const val MCC_CODE_INFO_MAX_LINES = 2
