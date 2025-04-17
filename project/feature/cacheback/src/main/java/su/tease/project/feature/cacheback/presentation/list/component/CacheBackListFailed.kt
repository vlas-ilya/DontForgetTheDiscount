package su.tease.project.feature.cacheback.presentation.list.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListError

@Composable
fun CacheBackListFailed(
    error: State<LoadCacheBackBankListError?>,
    onTryAgain: () -> Unit,
) {
}
