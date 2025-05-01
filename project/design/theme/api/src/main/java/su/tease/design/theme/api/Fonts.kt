package su.tease.design.theme.api

import androidx.compose.runtime.Immutable
import su.tease.design.theme.api.utils.FontSetting

@Immutable
interface Fonts {
    val text: FontSetting
    val title: FontSetting
    val h1: FontSetting
    val placeholder: FontSetting
    val smallTitle: FontSetting
    val smallInfo: FontSetting
    val button: FontSetting
}
