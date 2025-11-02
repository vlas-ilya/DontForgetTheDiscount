package su.tease.project.feature.preset.presentation.mcc.info.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.edit.DFTextField
import su.tease.project.design.component.controls.list.LazyListWrapper
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.mcc.component.MccCodePresetPreview
import su.tease.project.feature.preset.presentation.mcc.utils.toUi
import su.tease.project.design.icons.R as RIcons

class ListMccCodePresetPage(
    store: Store<*>,
    resourceProvider: ResourceProvider,
    private val presetInteractor: PresetInteractor,
) : BasePageComponent<ListMccCodePresetPage.Target>(store) {

    private val lazyListWrapper = LazyListWrapper(resourceProvider, SCROLL_ITEMS_FOR_SHOW_BUTTON)

    @Composable
    override fun invoke() {
        val searchTerm = remember { mutableStateOf("") }
        val list = memoize { presetInteractor.mccCodePresets() }
            .map { it?.filter(searchTerm.value) }
            .map { it?.toUi(store, null) }

        val (isScrollTopButtonVisible, scrollDirection, nestedScrollConnection, _, scrollUp) = lazyListWrapper.scrollState

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.angle_up,
                        onClick = { scrollUp() },
                        type = DFPageFloatingButton.Type.GRAY,
                        isVisible = isScrollTopButtonVisible.value
                    )
                )
            }
        }

        DFPage(
            title = stringResource(R.string.Presets_MccCodesListPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            additionalTitleContent = {
                AnimatedVisibility(
                    visible = scrollDirection.value == ScrollDirection.BOTTOM,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    DFTextField(
                        text = searchTerm,
                        placeholder = stringResource(R.string.Presets_MccCodesListPage_SearchInput_Placeholder),
                        onChange = { searchTerm.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.sizes.padding4),
                        maxLength = SEARCH_MAX_LENGTH,
                        shape = RoundedCornerShape(Theme.sizes.round16)
                    )
                }
            }
        ) {
            val mccCodes = list.value ?: run {
                lazyListWrapper.Shimmer(
                    count = SHIMMER_ITEM_COUNT,
                    modifier = Modifier.fillMaxWidth(),
                    itemContent = { MccCodePresetPreview.Shimmer(it) },
                    verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                    contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                )
                return@DFPage
            }
            lazyListWrapper.Compose(
                count = mccCodes.size,
                modifier = Modifier.fillMaxWidth().nestedScroll(nestedScrollConnection),
                itemContent = mccCodes::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            )
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page

    companion object {
        operator fun invoke() = Target
    }
}

private fun PersistentList<MccCodePreset>.filter(searchTerm: String) = filter {
    it.code.contains(searchTerm) || it.name.contains(searchTerm) || it.info.contains(searchTerm)
}.toPersistentList()

private const val SHIMMER_ITEM_COUNT = 20
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
private const val SEARCH_MAX_LENGTH = 20
