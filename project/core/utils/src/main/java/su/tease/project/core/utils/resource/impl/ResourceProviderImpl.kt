package su.tease.project.core.utils.resource.impl

import android.content.Context
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import su.tease.project.core.utils.resource.ResourceProvider

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    override fun dpToPx(dp: Dp): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.value,
        context.resources.displayMetrics
    )

    override fun string(id: Int) = context.getString(id)
}
