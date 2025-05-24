package su.tease.project.feature.cashback.presentation.save.cashback.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

interface SaveCashBackAction : MviUseCase<SaveCashBackRequest>

@Parcelize
sealed class SaveCashBackActions : PlainAction {
    data class OnInit(val request: SaveCashBackRequest?) : SaveCashBackActions()
    data class OnSetDate(val date: CashBackDate) : SaveCashBackActions()
    data object OnSave : SaveCashBackActions()
    data class OnSaveSuccess(val cashBack: CashBack) : SaveCashBackActions()
    data object OnSaveFail : SaveCashBackActions()
}

@Parcelize
data class SaveCashBackRequest(
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
) : Parcelable
