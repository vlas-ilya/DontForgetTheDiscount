package su.tease.core.mvi.navigation

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.stack.Stack
import su.tease.project.core.utils.stack.add
import su.tease.project.core.utils.stack.dropLastWhile
import su.tease.project.core.utils.stack.moveToUp
import su.tease.project.core.utils.stack.removeLast
import kotlin.reflect.KClass

@Parcelize
data class FeatureNavigation(
    val name: NavigationTarget.Feature,
    val initPage: NavigationTarget.Page,
    val stack: Stack<PageNavigation> = Stack(prev = null, value = PageNavigation(initPage))
) : Navigation {

    @IgnoredOnParcel
    override val page
        get() = stack.value.page

    @IgnoredOnParcel
    private val compare: (PageNavigation, PageNavigation) -> Boolean =
        { o1, o2 -> o1.name.some(o2.name) }

    fun forward(page: NavigationTarget.Page, singleTop: Boolean = false): FeatureNavigation = copy(
        stack = if (singleTop) stack.moveToUp(PageNavigation(page), compare)
        else stack.add(PageNavigation(page))
    )

    fun back(): FeatureNavigation? = stack
        .removeLast()
        ?.let { copy(stack = it) }

    fun backToPage(page: NavigationTarget.Page): FeatureNavigation? = stack
        .dropLastWhile { compare(it, PageNavigation(page)).not() }
        ?.let { copy(stack = it) }

    fun backToPage(page: KClass<NavigationTarget.Page>): FeatureNavigation? = stack
        .dropLastWhile { it.name::class != page }
        ?.let { copy(stack = it) }

    fun some(feature: FeatureNavigation) = name.some(feature.name)
}

fun feature(
    feature: NavigationTarget.Feature,
    initPage: NavigationTarget.Page,
) = FeatureNavigation(
    name = feature,
    initPage = initPage,
)