package su.tease.project.feature.cacheback.data.dataSource

import retrofit2.http.GET
import su.tease.project.feature.cacheback.data.dataSource.dto.BankDto

interface BankDictionary {

    @GET("api/dictionary/banks")
    suspend fun list(): List<BankDto>
}
