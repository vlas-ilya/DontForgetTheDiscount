package su.tease.project.feature.cacheback.presentation

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.transformIf
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction
import su.tease.project.feature.cacheback.presentation.add.page.BankSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.CodesSelectPage
import su.tease.project.feature.cacheback.presentation.add.page.IconSelectPage
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction as Add
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction as LoadList

@Parcelize
data class CacheBackState(
    val status: LoadingStatus = Init,
    val list: PersistentList<Bank> = persistentListOf(),
    val addForm: AddFormState = AddFormState(),
    val error: Boolean = false,
) : State

@Parcelize
data class AddFormState(
    val status: LoadingStatus = Init,
    val bank: BankPreset? = null,
    val name: CacheBackName? = null,
    val info: CacheBackInfo? = null,
    val icon: IconPreset? = null,
    val size: CacheBackSize? = null,
    val codes: PersistentList<CacheBackCode> = persistentListOf(),
    val errors: AddFormStateValidationErrors = AddFormStateValidationErrors(),
) : Parcelable

@Parcelize
data class AddFormStateValidationErrors(
    val bank: Boolean = false,
    val name: Boolean = false,
    val info: Boolean = false,
    val icon: Boolean = false,
) : Parcelable

class CacheBackReducer : Reducer<CacheBackState> {

    override val initState = CacheBackState()

    override fun CacheBackState.onAction(action: PlainAction) = when (action) {
        is LoadList -> onLoadList(action)
        is Add -> onAdd(action)
        is BankSelectPage.OnSelectAction -> onBankSelect(action)
        is IconSelectPage.OnSelectAction -> onIconSelect(action)
        is CodesSelectPage.OnSelectAction -> onCodesSelect(action)
        else -> this
    }

    private fun CacheBackState.onLoadList(action: LoadList) =
        when (action) {
            is LoadList.OnLoad -> copy(status = Loading, list = persistentListOf(), error = false)
            is LoadList.OnSuccess -> copy(status = Success, list = action.list, error = false)
            is LoadList.OnFail -> copy(status = Failed, error = true)
        }

    private fun CacheBackState.onAdd(action: Add) = changeAddForm {
        when (action) {
            is Add.OnInit -> AddFormState()
            is Add.OnNameChange -> copy(name = action.name)
            is Add.OnInfoChange -> copy(info = action.info)
            is Add.OnSizeChange -> copy(size = action.size)
            is Add.OnSave -> copy(status = Loading)
            is Add.OnSaveSuccess -> AddFormState()
            is Add.OnSaveFail -> copy(status = Failed)
            is AddCacheBackAction.OnValidationFail -> copy(errors = action.errors)
        }
    }

    private inline fun CacheBackState.changeAddForm(
        transformation: AddFormState.() -> AddFormState
    ) = copy(addForm = transformation(addForm))

    private fun CacheBackState.onBankSelect(action: BankSelectPage.OnSelectAction) =
        changeAddForm {
            transformIf(action.target.current()) {
                addForm.copy(bank = action.selected)
            }
        }

    private fun CacheBackState.onIconSelect(action: IconSelectPage.OnSelectAction) =
        changeAddForm {
            transformIf(action.target.current()) {
                addForm.copy(icon = action.selected)
            }
        }

    private fun CacheBackState.onCodesSelect(action: CodesSelectPage.OnSelectAction) =
        changeAddForm {
            transformIf(action.target.current()) {
                addForm.copy(codes = action.selected)
            }
        }

    companion object {
        private fun String.current() = this == CacheBackReducer::class.java.name
    }
}
