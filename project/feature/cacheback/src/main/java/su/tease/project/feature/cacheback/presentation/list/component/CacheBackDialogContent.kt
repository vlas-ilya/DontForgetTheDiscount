package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.design.component.controls.button.DFButton
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.component.controls.text.DFTextH1
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.mapper.toMonthYear

@Composable
fun CacheBackDialogContent(
    data: Pair<Bank, CacheBack>,
    onClick: () -> Unit,
    dateProvider: DateProvider,
) {
    val (bank, cacheBack) = data
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DFImage(
            url = cacheBack.icon.url,
            contentDescription = stringResource(
                R.string.page_cache_back_list_dialog_cache_back_content_description_icon,
                cacheBack.name.value,
            ),
            modifier = Modifier
                .clip(CircleShape)
                .background(Theme.colors.inputBackground)
                .padding(Theme.sizes.padding8)
                .size(Theme.sizes.size46)
        )
        Spacer(Modifier.width(Theme.sizes.padding4))
        DFTextH1(cacheBack.name.value)
    }
    Spacer(Modifier.height(Theme.sizes.padding4))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DFImage(
            url = bank.icon.url,
            modifier = Modifier
                .padding(horizontal = Theme.sizes.padding8)
                .clip(CircleShape)
                .size(Theme.sizes.size32),
            contentDescription = stringResource(
                R.string.page_cache_back_list_dialog_cache_back_content_description_bank_icon,
                bank.name.value,
            )
        )
        DFText(
            text = bank.name.value,
            style = Theme.fonts.placeholder,
            modifier = Modifier.padding(horizontal = Theme.sizes.padding6)
        )
    }
    Spacer(Modifier.height(Theme.sizes.padding4))
    DFText(cacheBack.info.value)
    Spacer(Modifier.height(Theme.sizes.padding4))
    DFText(cacheBack.size.percent.toString())
    Spacer(Modifier.height(Theme.sizes.padding4))
    DFText(dateProvider.toText(cacheBack.date.toMonthYear()))
    Spacer(Modifier.height(Theme.sizes.padding4))
    DFText(cacheBack.codes.joinToString { it.code.value })
    Spacer(Modifier.height(Theme.sizes.padding4))
    DFButton(
        label = stringResource(R.string.page_cache_back_list_dialog_button_close),
        onClick = onClick
    )
}