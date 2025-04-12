package su.tease.project.core.mvi.api.action

import androidx.compose.runtime.Immutable

@Immutable
@Deprecated(
    message = "Use PlainAction for simple actions",
    replaceWith = ReplaceWith("PlainAction")
)
interface Action
