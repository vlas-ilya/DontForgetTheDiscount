package su.tease.dontforgetthediscount.database

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import su.tease.dontforgetthediscount.database.migration.migration1to2Init
import su.tease.dontforgetthediscount.database.migration.migration2to3Init
import su.tease.dontforgetthediscount.database.migration.migration3to4Init

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get<Context>(),
            klass = DontForgetTheDiscountDatabase::class.java,
            name = "DontForgetTheDiscount"
        )
            .addMigrations(
                migration1to2Init,
                migration2to3Init,
                migration3to4Init,
            )
            .build()
    }

    dao { bankDao() }
    dao { cacheBackDao() }
    dao { cacheBackCodeDao() }
    dao { dictionaryDao() }
}
