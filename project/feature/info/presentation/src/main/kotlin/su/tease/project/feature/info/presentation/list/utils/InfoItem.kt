package su.tease.project.feature.info.presentation.list.utils

import androidx.annotation.StringRes
import su.tease.core.mvi.navigation.NavigationTarget

data class InfoItem(
    val id: String,
    @StringRes val name: Int,
    val navigation: NavigationTarget.Page
)
