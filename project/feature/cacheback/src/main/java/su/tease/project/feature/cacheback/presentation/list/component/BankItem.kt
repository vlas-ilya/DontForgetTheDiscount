package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.list.LazyListItem
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.Bank

data class BankItem(
    private val bankItem: Bank,
    private val store: Store<*>,
) : LazyListItem {

    override val key = bankItem.id.value

    @Composable
    override fun LazyItemScope.Compose() {
        Row(
            modifier = Modifier.fillParentMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DFImage(
                url = bankItem.icon.url,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(Theme.sizes.size32)
                    .background(Theme.colors.tmpFiller),
                contentDescription = stringResource(
                    R.string.item_bank_preview_content_description_bank_logo,
                    bankItem.name.value,
                )
            )
            DFText(
                text = bankItem.name.value,
                style = Theme.fonts.placeholder,
                modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
            )
        }
    }
}
