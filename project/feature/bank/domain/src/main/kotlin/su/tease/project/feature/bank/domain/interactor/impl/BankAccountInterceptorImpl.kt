package su.tease.project.feature.bank.domain.interactor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.domain.repository.BankAccountRepository

class BankAccountInterceptorImpl(
    private val repository: BankAccountRepository,
) : BankAccountInterceptor {

    override suspend fun save(bankAccount: BankAccount) = withDefault {
        repository.save(bankAccount)
    }

    override suspend fun get(id: String) = withDefault {
        repository.get(id)
    }

    override suspend fun list() = withDefault {
        repository.list()
    }

    override suspend fun listWithOutCashbacks() = withDefault {
        repository.listWithOutCashbacks()
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
