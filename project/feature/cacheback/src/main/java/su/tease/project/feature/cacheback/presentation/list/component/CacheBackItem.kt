package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.icon.DFIconButtonSize
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.design.icons.R as RIcons

data class CacheBackItem(
    private val cacheBack: CacheBack,
    private val store: Store<*>,
) : LazyListItem {

    override val key = cacheBack.id.value

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            Modifier
                .fillParentMaxWidth()
                .padding(start = Theme.sizes.padding20),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DFImage(
                url = cacheBack.icon.url,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Theme.colors.inputBackground)
                    .padding(Theme.sizes.padding8)
                    .size(Theme.sizes.size24),
                contentDescription = "",
                tint = Theme.colors.iconTint,
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
                        cacheBack.size.percent.toString(),
                    ),
                    style = Theme.fonts.monospace,
                )
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFText(
                    text = cacheBack.name.value,
                    style = Theme.fonts.text,
                )
            }
            runIf(cacheBack.info.value.isNotBlank() && cacheBack.codes.isNotEmpty()) {
                Spacer(modifier = Modifier.width(Theme.sizes.padding4))
                DFIconButton(
                    icon = RIcons.drawable.comment_info,
                    size = DFIconButtonSize.S,
                    onClick = {}
                )
            }
        }
    }
}
