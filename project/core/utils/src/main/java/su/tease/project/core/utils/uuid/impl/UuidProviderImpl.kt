package su.tease.project.core.utils.uuid.impl

import su.tease.project.core.utils.uuid.UuidProvider
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UuidProviderImpl : UuidProvider {

    @OptIn(ExperimentalUuidApi::class)
    override fun uuid(): String = Uuid.random().toString()
}
