package su.tease.project.feature.cashback.domain.usecase

import android.os.Parcelable
import kotlinx.collections.immutable.toPersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.repository.CashBackOwnerRepository
import su.tease.project.feature.cashback.domain.repository.PresetRepository

class SaveCashBackUseCase(
    private val cashBackOwnerRepository: CashBackOwnerRepository,
    private val presetRepository: PresetRepository,
    private val uuidProvider: UuidProvider,
) {
    suspend fun save(request: Request): CashBack {
        val cashBackOwner = cashBackOwnerRepository.get(request.owner.id)
        val cashBackPreset = presetRepository.get(request.preset.id)
        val cashBack = request.makeCashBack(cashBackPreset)
        val cashBacks = cashBackOwner.cashBacks.filter { it.id != cashBack.id }
        if (cashBacks.any { it.isDuplicateOf(cashBack) }) throw DuplicateException()
        updateCashBackOwner(cashBackOwner, cashBacks, cashBack)
        return cashBack
    }

    private fun Request.makeCashBack(preset: CashBackPreset) = CashBack(
        id = id ?: uuidProvider.uuid(),
        preset = preset,
        size = size,
        date = date,
        ownerId = owner.id,
    )

    private fun CashBack.isDuplicateOf(cashBack: CashBack) =
        preset.id == cashBack.preset.id && date == cashBack.date

    private suspend fun updateCashBackOwner(
        cashBackOwner: CashBackOwner,
        cashBacks: List<CashBack>,
        cashBack: CashBack
    ) {
        val updatedOwner = cashBackOwner.copy(cashBacks = (cashBacks + cashBack).toPersistentList())
        cashBackOwnerRepository.save(updatedOwner)
    }

    @Parcelize
    data class Request(
        val id: String?,
        val preset: CashBackPreset,
        val owner: CashBackOwner,
        val size: Int,
        val date: CashBackDate,
        val addMore: Boolean = false
    ) : Parcelable

    class DuplicateException : Exception()
}
