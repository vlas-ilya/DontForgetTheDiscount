package su.tease.project.feature.cacheback.presentation.save.cacheback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.ext.RedirectState
import su.tease.project.core.utils.ext.RedirectStateNotNull
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.ext.runIf
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.defaultCacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.SelectCacheBackPresetPage
import su.tease.project.feature.cacheback.presentation.save.bank.select.SelectBankAccountPage
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackAction
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.BankAccountSelect
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.CacheBackPresetSelect
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.DateSelect
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.SaveAndAddMoreButton
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.SaveButton
import su.tease.project.feature.cacheback.presentation.save.cacheback.component.SizeSelect
import su.tease.project.feature.cacheback.presentation.save.cacheback.reducer.SaveCacheBackReducer
import su.tease.project.feature.cacheback.presentation.save.cacheback.reducer.SaveCacheBackState
import su.tease.project.feature.cacheback.presentation.save.cacheback.utls.SaveCacheBackForm
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackActions as Save

class SaveCacheBackPage(
    store: Store<*>,
    private val target: Target,
    private val dateProvider: DateProvider,
    private val saveCacheBackAction: SaveCacheBackAction,
) : BasePageComponent<SaveCacheBackPage.Target>(store) {

    private val dateValue = target.saveCacheBackRequest?.date
        .takeIf { it !== defaultCacheBackDate }
        ?: dateProvider.current().toCacheBackDate()

    private val dates = dateProvider.currentAndNext().mapPersistent { it.toCacheBackDate() }

    private val form = SaveCacheBackForm(
        bankAccountValue = target.saveCacheBackRequest?.bankAccount,
        cacheBackPresetValue = target.saveCacheBackRequest?.cacheBackPreset,
        sizeValue = target.saveCacheBackRequest?.size,
        dateValue = dateValue,
    )

    init {
        dispatch(Save.OnInit(target.saveCacheBackRequest))
    }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(SaveCacheBackState::status).value

        LaunchedEffect(status) {
            if (status != LoadingStatus.Success) return@LaunchedEffect

            if (form.addMore.value.not()) {
                dispatch(Save.OnInit(target.saveCacheBackRequest))
                back()
            } else {
                val bank = form.bankAccount.value
                val date = form.date.value
                form.clean()
                dispatch(Save.OnInit(SaveCacheBackRequest(bankAccount = bank, date = date)))
            }
        }

        DFPage(
            title = stringResource(
                (target.saveCacheBackRequest?.id == null).choose(
                    R.string.page_save_cache_back_title_add,
                    R.string.page_save_cache_back_title_edit,
                )
            ),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            when (status) {
                LoadingStatus.Success -> return@DFPage
                LoadingStatus.Init -> CacheBackAddPageForm()
                LoadingStatus.Loading -> CacheBackAddPageLoading()
                LoadingStatus.Failed -> CacheBackAddPageFailed()
            }
        }
    }

    @Composable
    private fun CacheBackAddPageForm() {
        RedirectState(selectAsState(SaveCacheBackState::bankAccount), form.bankAccount)
        RedirectState(selectAsState(SaveCacheBackState::cacheBackPreset), form.cacheBackPreset)
        RedirectStateNotNull(selectAsState(SaveCacheBackState::date), form.date)

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.ime.asPaddingValues())
        ) {
            DateSelect(
                dateState = form.date,
                dates = dates,
                onSelect = { dispatch(Save.OnSetDate(it)) },
                modifier = Modifier.fillMaxWidth(),
                dateProvider = dateProvider,
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            BankAccountSelect(
                bankState = form.bankAccount,
                onSelect = { selectBankAccount() },
                error = form.ui { bankAccountError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CacheBackPresetSelect(
                cacheBackPreset = form.cacheBackPreset,
                enabled = form.cacheBackPresetEnabled,
                onSelect = { selectCacheBackPreset() },
                error = form.ui { cacheBackPresetError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            SizeSelect(
                sizeState = form.size,
                onChange = { form.size.value = it },
                error = form.ui { sizeError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            SaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { save(target.saveCacheBackRequest?.id) }
            )
            runIf(target.saveCacheBackRequest?.id == null) {
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                SaveAndAddMoreButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { saveAndAddMore() }
                )
            }
        }
    }

    private fun selectBankAccount() {
        SelectBankAccountPage<SaveCacheBackReducer>().forward()
    }

    private fun selectCacheBackPreset() {
        val bankPreset = form.bankAccount.value?.bankPreset ?: return
        SelectCacheBackPresetPage<SaveCacheBackReducer>(
            R.string.page_cache_back_preset_list_title_add,
            bankPreset,
        ).forward()
    }

    private fun save(cacheBackId: String?) {
        form.makeResult(cacheBackId)?.let {
            form.addMore.value = false
            dispatch(saveCacheBackAction(it))
        }
    }

    private fun saveAndAddMore() {
        form.makeResult()?.let {
            form.addMore.value = true
            dispatch(saveCacheBackAction(it))
        }
    }

    @Composable
    private fun CacheBackAddPageFailed() {
    }

    @Composable
    private fun CacheBackAddPageLoading() {
    }

    @Parcelize
    data class Target(val saveCacheBackRequest: SaveCacheBackRequest?) : NavigationTarget.Page

    companion object {
        operator fun invoke(
            saveCacheBackRequest: SaveCacheBackRequest?
        ) = Target(saveCacheBackRequest)
    }
}
