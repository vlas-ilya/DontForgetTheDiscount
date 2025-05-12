package su.tease.project.feature.cacheback.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.BankAccount

interface BankAccountInterceptor {
    suspend fun list(): PersistentList<BankAccount>
    suspend fun get(id: String): BankAccount
    suspend fun save(bankAccount: BankAccount)
}
