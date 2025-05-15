package su.tease.project.design.component.controls.page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.core.utils.ext.letIfNotNull
import su.tease.project.design.component.controls.icon.DFIconButton
import su.tease.project.design.component.controls.text.DFText
import su.tease.project.design.icons.R

@Composable
fun DFPageTitle(
    title: String,
    modifier: Modifier = Modifier,
    @DrawableRes actionIcon: Int? = null,
    additionalTitleContent: @Composable () -> Unit = {},
    onBackPress: (() -> Unit)? = null,
    onActionPress: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .padding(horizontal = Theme.sizes.padding4)
            .background(Theme.colors.background0)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Theme.sizes.size46),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            onBackPress?.let { onBackPressed ->
                DFIconButton(
                    icon = R.drawable.arrow_small_left,
                    tint = Theme.colors.headerText,
                    onClick = onBackPressed
                )
            } ?: Spacer(Modifier.size(Theme.sizes.size32))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                DFText(
                    text = title,
                    maxLines = 1,
                    color = Theme.colors.headerText,
                    style = Theme.fonts.title,
                    modifier = Modifier
                        .padding(horizontal = Theme.sizes.padding4)
                )
            }

            letIfNotNull(onActionPress, actionIcon) { onActionPressed, actionIcon ->
                DFIconButton(
                    icon = actionIcon,
                    tint = Theme.colors.headerText,
                    onClick = onActionPressed
                )
            } ?: Spacer(Modifier.size(Theme.sizes.size32))
        }
        additionalTitleContent()
    }
}
