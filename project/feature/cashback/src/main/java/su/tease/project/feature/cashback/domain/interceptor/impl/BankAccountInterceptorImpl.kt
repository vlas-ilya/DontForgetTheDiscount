package su.tease.project.feature.cashback.domain.interceptor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cashback.domain.repository.BankAccountRepository

class BankAccountInterceptorImpl(
    private val bankAccountRepository: BankAccountRepository,
) : BankAccountInterceptor {

    override suspend fun list() = withDefault {
        bankAccountRepository.list()
    }

    override suspend fun get(id: String) = withDefault {
        bankAccountRepository.get(id)
    }

    override suspend fun save(bankAccount: BankAccount) = withDefault {
        bankAccountRepository.save(bankAccount)
    }
}
