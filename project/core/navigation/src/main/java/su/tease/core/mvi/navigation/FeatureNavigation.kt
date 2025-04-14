package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.stack.Stack
import su.tease.project.core.utils.stack.add
import su.tease.project.core.utils.stack.dropLastWhile
import su.tease.project.core.utils.stack.moveToUp
import su.tease.project.core.utils.stack.removeLast

@Parcelize
data class FeatureNavigation(
    val name: NavigationTarget.Feature,
    val initPage: PageNavigation,
    val stack: Stack<PageNavigation> = Stack(prev = null, value = initPage)
) : Navigation {

    @IgnoredOnParcel
    override val top
        get() = stack.value.top

    @IgnoredOnParcel
    private val compare: (PageNavigation, PageNavigation) -> Boolean =
        { o1, o2 -> o1.name.some(o2.name) }

    fun forward(page: PageNavigation, singleTop: Boolean = false): FeatureNavigation = copy(
        stack = if (singleTop) stack.moveToUp(page, compare)
        else stack.add(page)
    )

    fun back(): FeatureNavigation? = stack
        .removeLast()
        ?.let { copy(stack = it) }

    fun backTo(page: PageNavigation): FeatureNavigation? = stack
        .dropLastWhile { compare(it, page).not() }
        ?.let { copy(stack = it) }
}
