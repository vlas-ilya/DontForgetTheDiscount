package su.tease.project.feature.cacheback.data.repository

import kotlinx.coroutines.async
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.ext.unit
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.data.dao.PresetDao
import su.tease.project.feature.cacheback.data.dao.entity.preset.PresetsVersionEntity
import su.tease.project.feature.cacheback.data.dataSource.PresetDataSource
import su.tease.project.feature.cacheback.data.dataSource.mapper.toEntity
import su.tease.project.feature.cacheback.data.dataSource.mapper.toEntityWithRelations
import su.tease.project.feature.cacheback.domain.repository.SyncPresetRepository

class SyncPresetRepositoryImpl(
    private val dataSource: PresetDataSource,
    private val dao: PresetDao,
    private val uuidProvider: UuidProvider,
) : SyncPresetRepository {

    override suspend fun sync() = withDefault {
        val remoteVersion = dataSource.version()
        val localVersion = dao.version() ?: PresetsVersionEntity(
            id = uuidProvider.uuid(),
            banks = 0,
            bankIcons = 0,
            cacheBackIcons = 0,
            mccCodes = 0,
        )

        val remoteBanks = async {
            (remoteVersion.banks > localVersion.banks).choose(
                dataSource.banks(),
                emptyList(),
            )
        }

        val remoteBankIcons = async {
            (remoteVersion.banks > localVersion.banks).choose(
                dataSource.bankIcons(),
                emptyList(),
            )
        }

        val remoteCacheBacksIcons = async {
            (remoteVersion.cacheBackIcons > localVersion.cacheBackIcons).choose(
                dataSource.cacheBackIcons(),
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
        val cacheBacksIcons = remoteCacheBacksIcons.await().mapPersistent { it.toEntity() }
        val mccCodes = remoteMccCodes.await().mapPersistent { it.toEntity() }
        val (cacheBacks, mccCodesRelations) = remoteBanks.await()
            .flatMap { bank -> bank.cacheBacks.map { it to bank.id } }
            .map { (cacheBack, bankId) -> cacheBack.toEntityWithRelations(bankId) }
            .unzip()

        bankIcons.forEach { dao.save(it) }
        banks.forEach { dao.save(it) }
        cacheBacksIcons.forEach { dao.save(it) }
        cacheBacks.forEach { dao.save(it) }
        mccCodes.forEach { dao.save(it) }
        mccCodesRelations.flatten().forEach { dao.save(it) }

        dao.save(
            PresetsVersionEntity(
                id = localVersion.id,
                banks = remoteVersion.banks,
                bankIcons = remoteVersion.bankIcons,
                cacheBackIcons = remoteVersion.cacheBackIcons,
                mccCodes = remoteVersion.mccCodes,
            )
        )
    }.unit()
}
