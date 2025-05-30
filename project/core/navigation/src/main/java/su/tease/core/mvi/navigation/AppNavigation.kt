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
data class AppNavigation(
    val name: NavigationTarget.App,
    val initFeature: FeatureNavigation,
    val stack: Stack<FeatureNavigation> = Stack(prev = null, value = initFeature),
    override val id: String = ImplicitUuid.make(),
) : Navigation {

    @IgnoredOnParcel
    val pageIdList: List<String> = stack.flatMap { it.pageIdList }

    @IgnoredOnParcel
    val featureIdList: List<String> = stack.map { it.id }

    @IgnoredOnParcel
    override val page
        get() = stack.value.page

    @IgnoredOnParcel
    val feature
        get() = stack.value

    @IgnoredOnParcel
    private val compare: (FeatureNavigation, FeatureNavigation) -> Boolean =
        { o1, o2 -> o1.name.some(o2.name) }

    fun forward(
        page: NavigationTarget.Page,
        singleTop: Boolean = false,
    ): AppNavigation? = stack
        .replaceLast(stack.value.forward(page, singleTop))
        ?.let { copy(stack = it) }

    fun forward(
        feature: FeatureNavigation,
        singleTop: Boolean = false,
    ): AppNavigation = copy(
        stack = if (singleTop) {
            stack.moveToUp(feature, compare)
        } else {
            stack.add(feature)
        },
    )

    fun switchTo(
        feature: FeatureNavigation,
        cleanStack: Boolean = false,
    ): AppNavigation? = feature
        .let { stack.last { it.name == feature.name } ?: feature }
        .transformIfNullable(cleanStack) { it.backToPage(it.initPage) }
        ?.let { stack.moveToUp(it, compare) }
        ?.let { copy(stack = it) }

    fun back(): AppNavigation? = stack
        .replaceLast(stack.value.back())
        ?.let { copy(stack = it) }

    fun backToPage(page: NavigationTarget.Page): AppNavigation? = stack
        .replaceLast(stack.value.backToPage(page))
        ?.let { copy(stack = it) }

    fun backToPage(page: KClass<NavigationTarget.Page>): AppNavigation? = stack
        .replaceLast(stack.value.backToPage(page))
        ?.let { copy(stack = it) }

    fun backToFeature(feature: FeatureNavigation): AppNavigation? = stack
        .dropLastWhile { compare(it, feature).not() }
        ?.let { copy(stack = it) }

    fun backToFeature(feature: KClass<FeatureNavigation>): AppNavigation? = stack
        .dropLastWhile { it.name::class != feature }
        ?.let { copy(stack = it) }

    fun finishFeature(feature: FeatureNavigation): AppNavigation? = stack
        .dropLastWhile { compare(it, feature).not() }
        ?.removeLast()
        ?.let { copy(stack = it) }

    fun finishFeature(feature: KClass<FeatureNavigation>): AppNavigation? = stack
        .dropLastWhile { it.name::class != feature }
        ?.removeLast()
        ?.let { copy(stack = it) }

    fun some(app: PageNavigation) = name.some(app.name)
}

fun app(
    app: NavigationTarget.App,
    initFeature: FeatureNavigation,
) = AppNavigation(
    name = app,
    initFeature = initFeature,
)
