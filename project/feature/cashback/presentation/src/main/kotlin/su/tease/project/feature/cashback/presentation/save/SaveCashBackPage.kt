@file:Suppress("EmptyFunctionBlock")

package su.tease.project.feature.cashback.presentation.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.mapper.toCashBackDate
import su.tease.project.feature.cashback.presentation.R
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerPreviewView
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerText
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackPresetIconView
import su.tease.project.feature.cashback.presentation.save.action.ExternalSaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackActions
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetAction
import su.tease.project.feature.cashback.presentation.save.component.CashBackDateSelect
import su.tease.project.feature.cashback.presentation.save.component.CashBackOwnerSelect
import su.tease.project.feature.cashback.presentation.save.component.CashBackPresetSelect
import su.tease.project.feature.cashback.presentation.save.component.CashBackSaveAndAddMoreButton
import su.tease.project.feature.cashback.presentation.save.component.CashBackSaveButton
import su.tease.project.feature.cashback.presentation.save.component.CashBackSizeSelect
import su.tease.project.feature.cashback.presentation.save.reducer.SaveCashBackState
import su.tease.project.feature.cashback.presentation.save.utls.SaveCashBackForm

class SaveCashBackPage(
    store: Store<*>,
    private val target: Target,
    private val dateProvider: DateProvider,
    private val saveCashBackAction: SaveCashBackAction,
    private val cashBackPresetIconView: CashBackPresetIconView,
    private val cashBackOwnerPreviewView: CashBackOwnerPreviewView,
    private val cashBackOwnerText: CashBackOwnerText,
    private val selectCashBackOwnerAction: SaveCashBackSelectCashBackOwnerAction,
    private val selectCashBackPresetAction: SaveCashBackSelectCashBackPresetAction,
) : BasePageComponent<SaveCashBackPage.Target>(store) {

    private val dateValue = target.date
        .takeIf { it !== defaultCashBackDate }
        ?: dateProvider.current().toCashBackDate()

    private val dates = dateProvider.currentAndNext().mapPersistent { it.toCashBackDate() }

    private val form = SaveCashBackForm(
        cashBackOwnerValue = target.owner,
        cashBackPresetValue = target.preset,
        sizeValue = target.size,
        dateValue = dateValue,
    )

    init {
        dispatch(SaveCashBackActions.OnInit(target))
    }

    override fun onFinish() {
        dispatch(ExternalSaveCashBackAction.OnFinish)
    }

    @Composable
    override operator fun invoke() {
        val status = selectAsState(SaveCashBackState::status).value

        LaunchedEffect(status) {
            if (status != LoadingStatus.Success) return@LaunchedEffect

            if (form.addMore.value.not()) {
                dispatch(SaveCashBackActions.OnInit())
            } else {
                val cashBackOwner = form.cashBackOwner.value
                val date = form.date.value
                form.clean()
                dispatch(
                    SaveCashBackActions.OnInit(
                        owner = cashBackOwner,
                        date = date
                    )
                )
            }
        }

        val isCreatingNew = target.id == null

        DFPage(
            title = stringResource(
                (isCreatingNew).choose(
                    R.string.SaveCashBack_AddPage_Title,
                    R.string.SaveCashBack_EditPage_Title,
                )
            ),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            when (status) {
                LoadingStatus.Init -> CashBackAddPageForm(isCreatingNew)
                LoadingStatus.Loading -> CashBackAddPageLoading()
                LoadingStatus.Success -> CashBackAddPageLoading()
                LoadingStatus.Failed -> CashBackAddPageForm(isCreatingNew)
            }
        }
    }

    @Composable
    private fun CashBackAddPageForm(
        isCreatingNew: Boolean
    ) {
        RedirectState(selectAsState(SaveCashBackState::cashBackOwner), form.cashBackOwner)
        RedirectState(selectAsState(SaveCashBackState::cashBackPreset), form.cashBackPreset)
        RedirectStateNotNull(selectAsState(SaveCashBackState::date), form.date)

        Column(
            modifier = Modifier
                .padding(Theme.sizes.padding8)
                .padding(top = Theme.sizes.padding6)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(WindowInsets.ime.asPaddingValues())
        ) {
            CashBackDateSelect(
                dateState = form.date,
                dates = dates,
                onSelect = { dispatch(SaveCashBackActions.OnSetDate(it)) },
                modifier = Modifier.fillMaxWidth(),
                dateProvider = dateProvider,
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CashBackOwnerSelect(
                cashBackOwnerState = form.cashBackOwner,
                onSelect = { dispatch(selectCashBackOwnerAction()) },
                error = form.ui { bankAccountError },
                disabled = !isCreatingNew,
                cashBackOwnerPreviewView = cashBackOwnerPreviewView,
                cashBackOwnerText = cashBackOwnerText,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CashBackPresetSelect(
                cashBackPreset = form.cashBackPreset,
                enabled = form.cashBackPresetEnabled,
                onSelect = { selectCashBackPreset() },
                error = form.ui { cashBackPresetError },
                cashBackPresetIconView = cashBackPresetIconView,
                cashBackOwnerText = cashBackOwnerText,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(Theme.sizes.padding4))
            CashBackSizeSelect(
                sizeState = form.size,
                onChange = { form.size.value = it },
                error = form.ui { sizeError },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1F))
            CashBackSaveButton(
                modifier = Modifier.wrapContentHeight(),
                onSubmit = { save(target.id) }
            )
            runIf(target.id == null) {
                Spacer(modifier = Modifier.height(Theme.sizes.padding4))
                CashBackSaveAndAddMoreButton(
                    modifier = Modifier.wrapContentHeight(),
                    onSubmit = { saveAndAddMore() }
                )
            }
        }
    }

    private fun selectCashBackPreset() {
        val ownerPreset = form.cashBackOwner.value?.preset ?: return
        dispatch(selectCashBackPresetAction(ownerPreset))
    }

    private fun save(cashBackId: String?) {
        form.makeResult(cashBackId)?.let {
            form.addMore.value = false
            dispatch(saveCashBackAction(it))
        }
    }

    private fun saveAndAddMore() {
        form.makeResult()?.let {
            form.addMore.value = true
            dispatch(saveCashBackAction(it.copy(addMore = true)))
        }
    }

    @Composable
    private fun CashBackAddPageLoading() {
        Shimmer {
            Box(
                modifier = Modifier
                    .padding(Theme.sizes.padding8)
                    .clip(RoundedCornerShape(Theme.sizes.round12))
                    .fillMaxSize()
                    .background(Theme.colors.shimmer)
            )
        }
    }

    @Parcelize
    data class Target(
        val id: String?,
        val preset: CashBackPreset?,
        val owner: CashBackOwner?,
        val size: Int?,
        val date: CashBackDate?,
    ) : NavigationTarget.Page

    companion object {
        operator fun invoke(
            id: String? = null,
            preset: CashBackPreset? = null,
            owner: CashBackOwner? = null,
            size: Int? = null,
            date: CashBackDate? = null,
        ) = Target(
            id = id,
            preset = preset,
            owner = owner,
            size = size,
            date = date,
        )

        @Suppress("MagicNumber")
        private val defaultCashBackDate = CashBackDate(0, 2025)
    }
}
