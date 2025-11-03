package su.tease.project.feature.bank.presentation.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.entity.CashBackPreset

interface SaveCacheBackAction : MviUseCase<SaveBankAccountActionRequest>

operator fun SaveCacheBackAction.invoke(
    id: String? = null,
    bankAccount: BankAccount? = null,
    cashBackPreset: CashBackPreset? = null,
    size: Int? = null,
    date: CashBackDate? = null,
) = invoke(
    SaveBankAccountActionRequest(
        id = id,
        bankAccount = bankAccount,
        cashBackPreset = cashBackPreset,
        size = size,
        date = date,
    )
)

@Parcelize
data class SaveBankAccountActionRequest(
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
) : Parcelable