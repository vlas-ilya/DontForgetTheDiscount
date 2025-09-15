package su.tease.project.feature.cashback.domain.interactor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.interactor.CashBackListInteractor
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId
import su.tease.project.feature.cashback.domain.repository.CashBackListRepository

class CashBackListInteractorImpl(
    private val repository: CashBackListRepository
) : CashBackListInteractor {

    override suspend fun listDates() = withDefault {
        repository.listDates()
    }

    override suspend fun listByOwnerId(ownerId: CashBackOwnerId) = withDefault {
        repository.listByOwnerId(ownerId)
    }

    override suspend fun listByOwnerIds(ownerIds: List<CashBackOwnerId>) = withDefault {
        repository.listByOwnerIds(ownerIds)
    }

    override suspend fun listByDate(date: CashBackDate) = withDefault {
        repository.listByDate(date)
    }

    override suspend fun save(cashBack: CashBack) = withDefault {
        repository.save(cashBack)
    }

    override suspend fun removeByOwnerId(ownerId: CashBackOwnerId) = withDefault {
        repository.removeByOwnerId(ownerId)
    }
}
