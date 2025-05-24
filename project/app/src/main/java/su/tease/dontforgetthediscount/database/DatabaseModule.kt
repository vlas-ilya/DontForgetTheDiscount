package su.tease.dontforgetthediscount.database

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import su.tease.dontforgetthediscount.database.migration.migration1to2Init

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get<Context>(),
            klass = DontForgetTheDiscountDatabase::class.java,
            name = "DontForgetTheDiscount"
        )
            .addMigrations(
                migration1to2Init,
            )
            .build()
    }

    dao { bankDao() }
    dao { cashBackDao() }
    dao { presetDao() }
}
