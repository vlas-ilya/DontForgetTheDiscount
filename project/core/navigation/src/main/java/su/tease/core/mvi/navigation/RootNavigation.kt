@file:Suppress("DEPRECATION")

package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.ext.transformIfNullable
import su.tease.project.core.utils.stack.Stack
import su.tease.project.core.utils.stack.add
import su.tease.project.core.utils.stack.dropLastWhile
import su.tease.project.core.utils.stack.flatMap
import su.tease.project.core.utils.stack.last
import su.tease.project.core.utils.stack.map
import su.tease.project.core.utils.stack.moveToUp
import su.tease.project.core.utils.stack.removeLast
import su.tease.project.core.utils.stack.replaceLast
import su.tease.project.core.utils.uuid.ImplicitUuid
import kotlin.reflect.KClass

@Parcelize
@Suppress("TooManyFunctions")
data class RootNavigation(
    val initApp: AppNavigation,
    val stack: Stack<AppNavigation> = Stack(prev = null, value = initApp),
    override val id: String = ImplicitUuid.make(),
) : Navigation {

    @IgnoredOnParcel
    val pageIdList: List<String> = stack.flatMap { it.pageIdList }

    @IgnoredOnParcel
    val featureIdList: List<String> = stack.flatMap { it.featureIdList }

    @IgnoredOnParcel
    val appIdList: List<String> = stack.map { it.id }

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

    fun forward(
        page: NavigationTarget.Page,
        singleTop: Boolean = false,
    ): RootNavigation? = stack
        .replaceLast(stack.value.forward(page, singleTop))
        ?.let { copy(stack = it) }

    fun forward(
        feature: FeatureNavigation,
        singleTop: Boolean = false,
    ): RootNavigation? = stack
        .replaceLast(stack.value.forward(feature, singleTop))
        ?.let { copy(stack = it) }

    fun forward(
        app: AppNavigation,
        singleTop: Boolean = false,
    ): RootNavigation = copy(
        stack = if (singleTop) {
            stack.moveToUp(app, compare)
        } else {
            stack.add(app)
        },
    )

    fun switchTo(
        feature: FeatureNavigation,
        cleanStack: Boolean = false,
    ): RootNavigation? = stack
        .replaceLast(stack.value.switchTo(feature, cleanStack))
        ?.let { copy(stack = it) }

    fun switchTo(
        app: AppNavigation,
        cleanStack: Boolean = false,
    ): RootNavigation? = app
        .let { stack.last { it.name == app.name } ?: app }
        .transformIfNullable(cleanStack) { it.backToFeature(it.initFeature) }
        ?.let { stack.moveToUp(it, compare) }
        ?.let { copy(stack = it) }

    fun back(): RootNavigation? = stack
        .replaceLast(stack.value.back())
        ?.let { copy(stack = it) }

    fun backToPage(page: NavigationTarget.Page): RootNavigation? = stack
        .replaceLast(stack.value.backToPage(page))
        ?.let { copy(stack = it) }

    fun backToPage(page: KClass<NavigationTarget.Page>): RootNavigation? = stack
        .replaceLast(stack.value.backToPage(page))
        ?.let { copy(stack = it) }

    fun backToFeature(feature: FeatureNavigation): RootNavigation? = stack
        .replaceLast(stack.value.backToFeature(feature))
        ?.let { copy(stack = it) }

    fun backToFeature(feature: KClass<FeatureNavigation>): RootNavigation? = stack
        .replaceLast(stack.value.backToFeature(feature))
        ?.let { copy(stack = it) }

    fun backToApp(app: AppNavigation): RootNavigation? = stack
        .dropLastWhile { compare(it, app).not() }
        ?.let { copy(stack = it) }

    fun backToApp(app: KClass<AppNavigation>): RootNavigation? = stack
        .dropLastWhile { it.name::class != app }
        ?.let { copy(stack = it) }

    fun finishFeature(feature: FeatureNavigation): RootNavigation? = stack
        .replaceLast(stack.value.finishFeature(feature))
        ?.let { copy(stack = it) }

    fun finishFeature(feature: KClass<FeatureNavigation>): RootNavigation? = stack
        .replaceLast(stack.value.finishFeature(feature))
        ?.let { copy(stack = it) }

    fun finishApp(app: AppNavigation): RootNavigation? = stack
        .dropLastWhile { compare(it, app).not() }
        ?.removeLast()
        ?.let { copy(stack = it) }

    fun finishApp(app: KClass<AppNavigation>): RootNavigation? = stack
        .dropLastWhile { it.name::class != app }
        ?.removeLast()
        ?.let { copy(stack = it) }

    fun replace(app: AppNavigation): RootNavigation = copy(
        stack = Stack(prev = null, value = app),
    )
}
