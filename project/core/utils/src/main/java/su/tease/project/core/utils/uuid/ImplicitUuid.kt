package su.tease.project.core.utils.uuid

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Deprecated(
    message = "Don't use implicit functionality",
    replaceWith = ReplaceWith("UuidProvider")
)
object ImplicitUuid {
    private var maker: () -> String = { Uuid.random().toString() }

    fun make() = maker()

    fun setMaker(maker: () -> String) {
        this.maker = maker
    }
}
