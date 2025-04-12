package su.tease.project.design.theme.impl.utils

import androidx.compose.foundation.layout.PaddingValues
import su.tease.design.theme.api.Sizes
import su.tease.project.design.theme.impl.ThemeValue

internal data class SizesImpl(
    override val pagePadding: PaddingValues
) : Sizes {

    companion object {
        internal fun make(themeValue: ThemeValue): Sizes = when (themeValue) {
            ThemeValue.LIGHT -> makeLightSizes()
            ThemeValue.NIGHT -> makeNightSizes()
        }
    }
}

private fun makeLightSizes(): Sizes = SizesImpl(

)

private fun makeNightSizes(): Sizes = SizesImpl(

)
