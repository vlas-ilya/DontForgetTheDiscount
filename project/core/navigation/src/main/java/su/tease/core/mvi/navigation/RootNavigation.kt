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
data class RootNavigation(
    val initApp: AppNavigation,
    private val stack: Stack<AppNavigation> = Stack(prev = null, value = initApp),
) : Navigation {

    @IgnoredOnParcel
    override val page
        get() = stack.value.page

    @IgnoredOnParcel
    val feature
        get() = stack.value.feature

    @IgnoredOnParcel
    val app
        get() = stack.value

    @IgnoredOnParcel
    private val compare: (AppNavigation, AppNavigation) -> Boolean =
        { o1, o2 -> o1.name.some(o2.name) }

    fun forward(page: PageNavigation, singleTop: Boolean = false): RootNavigation? = stack
        .replaceLast(stack.value.forward(page, singleTop))
        ?.let { copy(stack = it) }

    fun forward(feature: FeatureNavigation, singleTop: Boolean = false): RootNavigation? = stack
        .replaceLast(stack.value.forward(feature, singleTop))
        ?.let { copy(stack = it) }

    fun forward(app: AppNavigation, singleTop: Boolean = false): RootNavigation = copy(
        stack = if (singleTop) stack.moveToUp(app, compare)
        else stack.add(app)
    )

    fun switchTo(feature: FeatureNavigation, cleanStack: Boolean = false): RootNavigation? = stack
        .replaceLast(stack.value.switchTo(feature, cleanStack))
        ?.let { copy(stack = it) }

    fun switchTo(app: AppNavigation, cleanStack: Boolean = false): RootNavigation? = app
        .let { stack.last { it.name == app.name } ?: app }
        .tryTransformIf(cleanStack) { it.backTo(it.initFeature) }
        ?.let { stack.moveToUp(it, compare) }
        ?.let { copy(stack = it) }

    fun back(): RootNavigation? = stack
        .replaceLast(stack.value.back())
        ?.let { copy(stack = it) }

    fun backTo(page: PageNavigation): RootNavigation? = stack
        .replaceLast(stack.value.backTo(page))
        ?.let { copy(stack = it) }

    fun backTo(feature: FeatureNavigation): RootNavigation? = stack
        .replaceLast(stack.value.backTo(feature))
        ?.let { copy(stack = it) }

    fun backTo(app: AppNavigation): RootNavigation? = stack
        .dropLastWhile { compare(it, app).not() }
        ?.let { copy(stack = it) }

    fun finish(feature: FeatureNavigation): RootNavigation? = stack
        .replaceLast(stack.value.finish(feature))
        ?.let { copy(stack = it) }

    fun finish(app: AppNavigation): RootNavigation? = stack
        .dropLastWhile { compare(it, app).not() }
        ?.removeLast()
        ?.let { copy(stack = it) }

    fun replace(app: AppNavigation): RootNavigation = copy(
        stack = Stack(prev = null, value = app)
    )
}
