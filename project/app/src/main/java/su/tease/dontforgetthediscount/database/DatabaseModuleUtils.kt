package su.tease.dontforgetthediscount.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.core.module.Module
import timber.log.Timber

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
    crossinline migrate: RichSupportSQLiteDatabase.() -> Unit
) = Migration(from, to) {
    it.beginTransaction()
    try {
        migrate(RichSupportSQLiteDatabase(it))
        it.setTransactionSuccessful()
    } catch (t: Throwable) {
        Timber.e(t)
    } finally {
        it.endTransaction()
    }
}

class RichSupportSQLiteDatabase(
    supportSQLiteDatabase: SupportSQLiteDatabase
): SupportSQLiteDatabase by supportSQLiteDatabase {
    fun execRichSQL(sql: String) {
        sql.split(";")
            .map { it.trimIndent() }
            .filter { it.isNotBlank() }
            .forEach { execSQL(it) }
    }
}
