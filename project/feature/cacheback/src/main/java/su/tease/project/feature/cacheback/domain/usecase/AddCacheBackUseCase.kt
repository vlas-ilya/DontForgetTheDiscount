package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.AddFormStateValidationErrors

interface AddCacheBackUseCase : MviUseCase<AddFormState>

@Parcelize
sealed class AddCacheBackAction : PlainAction {

    data object OnInit : AddCacheBackAction()
    data class OnNameChange(val name: CacheBackName) : AddCacheBackAction()
    data class OnInfoChange(val info: CacheBackInfo) : AddCacheBackAction()
    data class OnSizeChange(val size: CacheBackSize) : AddCacheBackAction()
    data object OnSave : AddCacheBackAction()
    data class OnSaveSuccess(val cacheBack: CacheBack) : AddCacheBackAction()
    data object OnSaveFail : AddCacheBackAction()
    data class OnValidationFail(val errors: AddFormStateValidationErrors) : AddCacheBackAction()
}
