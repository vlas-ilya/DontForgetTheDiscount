package su.tease.project.design.theme.impl.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import su.tease.design.theme.api.Sizes
import su.tease.project.design.theme.impl.ThemeValue

internal data class SizesImpl(
    override val size0: Dp,
    override val size1: Dp,
    override val size2: Dp,
    override val size4: Dp,
    override val size6: Dp,
    override val size8: Dp,
    override val size10: Dp,
    override val size12: Dp,
    override val size16: Dp,
    override val size20: Dp,
    override val size24: Dp,
    override val size28: Dp,
    override val size32: Dp,
    override val size40: Dp,
    override val size50: Dp,
    override val padding1: Dp,
    override val padding2: Dp,
    override val padding4: Dp,
    override val padding6: Dp,
    override val padding8: Dp,
    override val padding10: Dp,
    override val padding12: Dp,
    override val padding20: Dp,
    override val round1: Dp,
    override val round2: Dp,
    override val round4: Dp,
    override val round6: Dp,
    override val round8: Dp,
    override val round10: Dp,
    override val round12: Dp,
    override val roundInfinity: Dp,
    override val elevation10: Dp,
) : Sizes {

    companion object {
        internal fun make(themeValue: ThemeValue): Sizes = when (themeValue) {
            ThemeValue.LIGHT -> makeLightSizes()
            ThemeValue.NIGHT -> makeNightSizes()
        }
    }
}

private fun makeLightSizes(): Sizes = SizesImpl(
    size0 = 0.dp,
    size1 = 1.dp,
    size2 = 2.dp,
    size4 = 4.dp,
    size6 = 6.dp,
    size8 = 8.dp,
    size10 = 10.dp,
    size12 = 12.dp,
    size16 = 16.dp,
    size20 = 20.dp,
    size24 = 24.dp,
    size28 = 28.dp,
    size32 = 32.dp,
    size40 = 40.dp,
    size50 = 50.dp,
    padding1 = 1.dp,
    padding2 = 2.dp,
    padding4 = 4.dp,
    padding6 = 6.dp,
    padding8 = 8.dp,
    padding10 = 10.dp,
    padding12 = 12.dp,
    padding20 = 20.dp,
    round1 = 1.dp,
    round2 = 2.dp,
    round4 = 4.dp,
    round6 = 6.dp,
    round8 = 8.dp,
    round10 = 10.dp,
    round12 = 12.dp,
    roundInfinity = Int.MAX_VALUE.dp,
    elevation10 = 10.dp,
)

private fun makeNightSizes(): Sizes = makeLightSizes()
