package su.tease.project.feature.shop.domain.interactor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.domain.repository.ShopRepository

class ShopInterceptorImpl(
    private val repository: ShopRepository,
) : ShopInterceptor {

    override suspend fun save(shop: Shop) = withDefault {
        repository.save(shop)
    }

    override suspend fun get(id: String) = withDefault {
        repository.get(id)
    }

    override suspend fun list() = withDefault {
        repository.list()
    }

    override suspend fun filterBy(date: CashBackDate) = withDefault {
        repository.filterBy(date)
    }

    override suspend fun listDates() = withDefault {
        repository.listDates()
    }

    override suspend fun delete(id: String) = withDefault {
        repository.delete(id)
    }
}
