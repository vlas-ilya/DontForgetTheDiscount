package su.tease.project.feature.cashback.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.usecase.SaveCashBackUseCase
import su.tease.project.feature.cashback.presentation.save.SaveCashBackPage

interface SaveCashBackAction : MviUseCase<SaveCashBackUseCase.Request>

@Parcelize
sealed class ExternalSaveCashBackAction : PlainAction {
    data class OnSaved(val cashBack: CashBack) : ExternalSaveCashBackAction()
    data object OnFinish : ExternalSaveCashBackAction()
}

@Parcelize
sealed class SaveCashBackActions : PlainAction {
    data class OnInit(
        val id: String? = null,
        val preset: CashBackPreset? = null,
        val owner: CashBackOwner? = null,
        val size: Int? = null,
        val date: CashBackDate? = null,
    ) : SaveCashBackActions() {

        constructor(request: SaveCashBackUseCase.Request) : this(
            id = request.id,
            preset = request.preset,
            owner = request.owner,
            size = request.size,
            date = request.date,
        )

        constructor(target: SaveCashBackPage.Target) : this(
            id = target.id,
            preset = target.preset,
            owner = target.owner,
            size = target.size,
            date = target.date,
        )
    }

    data class OnSetDate(val date: CashBackDate) : SaveCashBackActions()
    data object OnSave : SaveCashBackActions()
    data object OnSaved : SaveCashBackActions()
    data object OnSaveFail : SaveCashBackActions()
}
