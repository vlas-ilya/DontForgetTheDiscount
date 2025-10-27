package su.tease.project.feature.bank.presentation.list.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import su.tease.project.design.component.controls.list.LazyListWrapper

@Composable
@NonSkippableComposable
fun BankAccountsPageInit(lazyListWrapper: LazyListWrapper) = BankAccountsPageLoading(lazyListWrapper)
