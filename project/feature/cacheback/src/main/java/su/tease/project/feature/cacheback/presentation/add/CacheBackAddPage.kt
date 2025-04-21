package su.tease.project.feature.cacheback.presentation.add

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.CacheBackReducer
import su.tease.project.feature.cacheback.presentation.CacheBackState
import su.tease.project.feature.cacheback.presentation.add.component.BankSelect
import su.tease.project.feature.cacheback.presentation.add.component.CacheBackAddPageFailed
import su.tease.project.feature.cacheback.presentation.add.component.CacheBackAddPageLoading
import su.tease.project.feature.cacheback.presentation.add.component.CodesSelect
import su.tease.project.feature.cacheback.presentation.add.component.IconSelect
import su.tease.project.feature.cacheback.presentation.add.component.InfoEditText
import su.tease.project.feature.cacheback.presentation.add.component.NameEditText
import su.tease.project.feature.cacheback.presentation.add.component.SaveButton
import su.tease.project.feature.cacheback.presentation.add.component.SizeSelect
import su.tease.project.feature.cacheback.presentation.add.page.BankSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add

class CacheBackAddPage<S : State>(
    store: Store<S>,
    private val addCacheBackUseCase: AddCacheBackUseCase,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    override fun RootContainerConfiguration.configure() {
        isFullscreen = true
    }

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override fun Compose() {
        val status = select(status).collectAsState(null).value

        LaunchedEffect(status) { if (status == LoadingStatus.Success) back() }

        when (status) {
            null -> return
            LoadingStatus.Success -> return
            LoadingStatus.Init -> CacheBackAddPageForm()
            LoadingStatus.Loading -> CacheBackAddPageLoading()
            LoadingStatus.Failed -> CacheBackAddPageFailed()
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        val bank = select(bank).collectAsState(null)
        val name = select(name).collectAsState(null)
        val info = select(info).collectAsState(null)
        val icon = select(icon).collectAsState(null)
        val size = select(size).collectAsState(null)
        val codes = select(codes).collectAsState(null)
        val addForm = select(addForm).collectAsState(null)

        Row {
            BankSelect(
                bank = bank,
                onSelect = { forward(BankSelectPage<CacheBackReducer>(bank.value)) },
            )
            NameEditText(
                name = name,
                onChange = { dispatch(Add.OnNameChange(it)) },
            )
            InfoEditText(
                info = info,
                onChange = { dispatch(Add.OnInfoChange(it)) },
            )
            IconSelect(
                icon = icon,
                onSelect = { forward(IconSelectPage<CacheBackReducer>(icon.value)) },
            )
            SizeSelect(
                size = size,
                onChange = { dispatch(Add.OnSizeChange(it)) }
            )
            CodesSelect(
                codes = codes,
                onSelect = { forward(CodesSelectPage<CacheBackReducer>(codes.value)) }
            )
            SaveButton {
                addForm.value?.let { dispatch(addCacheBackUseCase(it)) }
            }
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private val status = Selector<CacheBackState, LoadingStatus?> { addForm.status }
private val addForm = Selector<CacheBackState, AddFormState?> { addForm }
private val bank = Selector<CacheBackState, BankPreset?> { addForm.bank }
private val name = Selector<CacheBackState, CacheBackName?> { addForm.name }
private val info = Selector<CacheBackState, CacheBackInfo?> { addForm.info }
private val icon = Selector<CacheBackState, IconPreset?> { addForm.icon }
private val size = Selector<CacheBackState, CacheBackSize?> { addForm.size }
private val codes = Selector<CacheBackState, PersistentList<CacheBackCode>?> { addForm.codes }
