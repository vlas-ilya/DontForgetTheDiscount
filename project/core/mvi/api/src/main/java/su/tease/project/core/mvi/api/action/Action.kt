package su.tease.project.core.mvi.api.action

import androidx.compose.runtime.Immutable

@Immutable
@Deprecated(
    message = """
        Use PlainAction for simple actions.
        Possible to use only for generic actions 
        like PlainAction and SuspendAction
        """,
    replaceWith = ReplaceWith("PlainAction"),
    level = DeprecationLevel.WARNING,
)
interface Action
