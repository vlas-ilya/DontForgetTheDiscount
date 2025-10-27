package su.tease.project.feature.shop.presentation.list.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import su.tease.project.design.component.controls.list.LazyListWrapper

@Composable
@NonSkippableComposable
fun ListCashBackInit(lazyListWrapper: LazyListWrapper) = ListCashBackLoading(lazyListWrapper)
