package su.tease.project.feature.preset.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.withTimeout
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.data.dao.PresetDao
import su.tease.project.feature.preset.data.dao.entity.PresetsVersionEntity
import su.tease.project.feature.preset.data.dataSource.PresetDataSource
import su.tease.project.feature.preset.data.dataSource.mapper.toEntity
import su.tease.project.feature.preset.data.dataSource.mapper.toEntityWithRelations
import su.tease.project.feature.preset.domain.repository.SyncPresetRepository

class SyncPresetRepositoryImpl(
    private val dataSource: PresetDataSource,
    private val dao: PresetDao,
    private val uuidProvider: UuidProvider,
) : SyncPresetRepository {

    override suspend fun sync() = withDefault {
        withTimeout(TIME_MILLIS) {
            val remoteVersion = dataSource.version()
            val localVersion = dao.version() ?: PresetsVersionEntity(
                id = uuidProvider.uuid(),
                banks = 0,
                bankIcons = 0,
                shops = 0,
                shopIcons = 0,
                cashBackIcons = 0,
                mccCodes = 0,
            )

            val remoteBanks = async {
                (remoteVersion.banks > localVersion.banks).choose(
                    dataSource.banks(),
                    emptyList(),
                )
            }

            val remoteBankIcons = async {
                (remoteVersion.bankIcons > localVersion.bankIcons).choose(
                    dataSource.bankIcons(),
                    emptyList(),
                )
            }

            val remoteShops = async {
                (remoteVersion.shops > localVersion.banks).choose(
                    dataSource.shops(),
                    emptyList(),
                )
            }

            val remoteShopIcons = async {
                (remoteVersion.shopIcons > localVersion.shopIcons).choose(
                    dataSource.shopIcons(),
                    emptyList(),
                )
            }

            val remoteCashBacksIcons = async {
                (remoteVersion.cashBackIcons > localVersion.cashBackIcons).choose(
                    dataSource.cashBackIcons(),
                    emptyList(),
                )
            }

            val remoteMccCodes = async {
                (remoteVersion.mccCodes > localVersion.mccCodes).choose(
                    dataSource.mccCodes(),
                    emptyList(),
                )
            }

            val banks = remoteBanks.await().mapPersistent { it.toEntity() }
            val bankIcons = remoteBankIcons.await().mapPersistent { it.toEntity() }
            val shops = remoteShops.await().mapPersistent { it.toEntity() }
            val shopIcons = remoteShopIcons.await().mapPersistent { it.toEntity() }
            val cashBacksIcons = remoteCashBacksIcons.await().mapPersistent { it.toEntity() }
            val mccCodes = remoteMccCodes.await().mapPersistent { it.toEntity() }
            val (cashBacks, mccCodesRelations) = remoteBanks.await()
                .flatMap { bank -> bank.cashBacks.map { it to bank.id } }
                .map { (cashBack, bankId) -> cashBack.toEntityWithRelations(bankId) }
                .unzip()

            bankIcons.forEach { dao.save(it) }
            banks.forEach { dao.save(it) }
            shopIcons.forEach { dao.save(it) }
            shops.forEach { dao.save(it) }
            cashBacksIcons.forEach { dao.save(it) }
            cashBacks.forEach { dao.save(it) }
            mccCodes.forEach { dao.save(it) }
            mccCodesRelations.flatten().forEach { dao.save(it) }

            dao.save(
                PresetsVersionEntity(
                    id = localVersion.id,
                    banks = remoteVersion.banks,
                    bankIcons = remoteVersion.bankIcons,
                    shops = remoteVersion.shops,
                    shopIcons = remoteVersion.shopIcons,
                    cashBackIcons = remoteVersion.cashBackIcons,
                    mccCodes = remoteVersion.mccCodes,
                )
            )
        }
    }.unit()

    private companion object {
        const val TIME_MILLIS = 3 * 1000L
    }
}
