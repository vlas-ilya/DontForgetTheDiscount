package su.tease.project.core.mvi.api.state

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlin.reflect.KClass

@Immutable
interface State : Parcelable {
    fun findState(clazz: KClass<out State>): State? = if (this::class == clazz) this else null
}
