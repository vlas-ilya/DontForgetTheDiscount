package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.ext.tryTransformIf
import su.tease.project.core.utils.stack.Stack
import su.tease.project.core.utils.stack.add
import su.tease.project.core.utils.stack.dropLastWhile
import su.tease.project.core.utils.stack.last
import su.tease.project.core.utils.stack.moveToUp
import su.tease.project.core.utils.stack.removeLast
import su.tease.project.core.utils.stack.replaceLast

@Parcelize
data class AppNavigation(
    val name: NavigationTarget.App,
    val initFeature: FeatureNavigation,
    val stack: Stack<FeatureNavigation> = Stack(prev = null, value = initFeature),
) : Navigation {

    @IgnoredOnParcel
    override val page
        get() = stack.value.page

    @IgnoredOnParcel
    val feature
        get() = stack.value

    @IgnoredOnParcel
    private val compare: (FeatureNavigation, FeatureNavigation) -> Boolean =
        { o1, o2 -> o1.name.some(o2.name) }

    fun forward(page: PageNavigation, singleTop: Boolean = false): AppNavigation? = stack
        .replaceLast(stack.value.forward(page, singleTop))
        ?.let { copy(stack = it) }

    fun forward(feature: FeatureNavigation, singleTop: Boolean = false): AppNavigation = copy(
        stack = if (singleTop) stack.moveToUp(feature, compare)
        else stack.add(feature)
    )

    fun switchTo(feature: FeatureNavigation, cleanStack: Boolean = false): AppNavigation? = feature
        .let { stack.last { it.name == feature.name } ?: feature }
        .tryTransformIf(cleanStack) { it.backTo(it.initPage) }
        ?.let { stack.moveToUp(it, compare) }
        ?.let { copy(stack = it) }

    fun back(): AppNavigation? = stack
        .replaceLast(stack.value.back())
        ?.let { copy(stack = it) }

    fun backTo(page: PageNavigation): AppNavigation? = stack
        .replaceLast(stack.value.backTo(page))
        ?.let { copy(stack = it) }

    fun backTo(feature: FeatureNavigation): AppNavigation? = stack
        .dropLastWhile { compare(it, feature).not() }
        ?.let { copy(stack = it) }

    fun finish(feature: FeatureNavigation): AppNavigation? = stack
        .dropLastWhile { compare(it, feature).not() }
        ?.removeLast()
        ?.let { copy(stack = it) }
}
