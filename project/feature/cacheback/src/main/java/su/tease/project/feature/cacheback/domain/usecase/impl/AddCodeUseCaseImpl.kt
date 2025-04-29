package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeValue
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackCodePreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCase
import su.tease.project.feature.cacheback.domain.usecase.AddCodeUseCaseRequest
import su.tease.project.feature.cacheback.domain.usecase.OnAddCodeActionInit
import su.tease.project.feature.cacheback.presentation.select.code.CodesSelectPage

class AddCodeUseCaseImpl(
    private val dictionaryInterceptor: DictionaryInterceptor,
    private val uuidProvider: UuidProvider,
) : AddCodeUseCase {

    override fun run(request: AddCodeUseCaseRequest) = suspendAction {
        dispatch(OnAddCodeActionInit)
        val codes = savePresets(request.codes).mapPersistent { CacheBackCode(it.id, it.code) }
        dispatch(CodesSelectPage.OnSelectAction(request.target, codes))
    }

    private suspend fun savePresets(codes: List<String>): List<CacheBackCodePreset> {
        val persisted = dictionaryInterceptor.cacheBacksCodes().associateBy { it.code.value }
        return codes.map {
            CacheBackCodePreset(
                id = persisted[it]?.id ?: CacheBackCodeId(uuidProvider.uuid()),
                code = persisted[it]?.code ?: CacheBackCodeValue(it),
                usageCount = (persisted[it]?.usageCount ?: 0) + 1
            )
        }.onEach {
            dictionaryInterceptor.save(it)
        }
    }
}