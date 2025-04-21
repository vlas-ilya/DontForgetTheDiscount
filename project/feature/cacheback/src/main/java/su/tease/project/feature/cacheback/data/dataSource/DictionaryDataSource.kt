package su.tease.project.feature.cacheback.data.dataSource

import retrofit2.http.GET
import su.tease.project.feature.cacheback.data.dataSource.dto.BankPresetDto
import su.tease.project.feature.cacheback.data.dataSource.dto.CacheBackIconDto
import su.tease.project.feature.cacheback.data.dataSource.dto.CacheBackPresetDto

interface DictionaryDataSource {

    @GET("api/dictionary/bank/list")
    suspend fun banks(): List<BankPresetDto>

    @GET("api/dictionary/cacheBack/list")
    suspend fun cacheBacks(): List<CacheBackPresetDto>

    @GET("api/dictionary/icon/cacheBack")
    suspend fun cacheBacksIcons(): List<CacheBackIconDto>
}
