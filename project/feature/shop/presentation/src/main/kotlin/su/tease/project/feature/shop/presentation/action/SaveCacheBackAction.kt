package su.tease.project.feature.shop.presentation.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.CashBackPreset
import su.tease.project.feature.shop.domain.entity.Shop

interface SaveCacheBackAction : MviUseCase<SaveBankAccountActionRequest>

operator fun SaveCacheBackAction.invoke(
    id: String? = null,
    shop: Shop? = null,
    cashBackPreset: CashBackPreset? = null,
    size: Int? = null,
    date: CashBackDate? = null,
) = invoke(
    SaveBankAccountActionRequest(
        id = id,
        shop = shop,
        cashBackPreset = cashBackPreset,
        size = size,
        date = date,
    )
)

@Parcelize
data class SaveBankAccountActionRequest(
    val id: String? = null,
    val shop: Shop? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
) : Parcelable
