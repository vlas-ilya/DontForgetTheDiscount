package su.tease.dontforgetthediscount.database.migration

import su.tease.dontforgetthediscount.database.migration

val migration2To3Percent = migration(2, 3) {
    execSQL(UPDATE_TABLE_CACHE_BACK_ENTITY_PERCENTS.trimIndent())
}

private const val UPDATE_TABLE_CACHE_BACK_ENTITY_PERCENTS = """
    UPDATE CacheBackEntity
    SET size = size * 100
"""