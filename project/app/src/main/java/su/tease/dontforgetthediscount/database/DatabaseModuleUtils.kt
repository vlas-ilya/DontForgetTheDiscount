package su.tease.dontforgetthediscount.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.core.module.Module

inline fun <reified T> Module.dao(
    crossinline provider: DontForgetTheDiscountDatabase.() -> T
) {
    factory<T> {
        val database = get<DontForgetTheDiscountDatabase>()
        provider(database)
    }
}

inline fun migration(
    from: Int,
    to: Int,
    crossinline migrate: SupportSQLiteDatabase.() -> Unit
) = Migration(from, to) {
    migrate(it)
}
