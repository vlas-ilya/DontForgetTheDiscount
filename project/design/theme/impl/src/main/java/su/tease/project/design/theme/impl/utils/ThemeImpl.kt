package su.tease.project.design.theme.impl.utils

import su.tease.design.theme.api.Colors
import su.tease.design.theme.api.Fonts
import su.tease.design.theme.api.Sizes
import su.tease.design.theme.api.Theme
import su.tease.project.design.theme.impl.ThemeValue

internal data class ThemeImpl(
    override val color: Colors,
    override val font: Fonts,
    override val size: Sizes,
) : Theme {

    companion object {
        internal fun make(themeValue: ThemeValue): Theme = ThemeImpl(
            color = ColorsImpl.make(themeValue),
            font = FontsImpl.make(themeValue),
            size = SizesImpl.make(themeValue),
        )
    }
}
