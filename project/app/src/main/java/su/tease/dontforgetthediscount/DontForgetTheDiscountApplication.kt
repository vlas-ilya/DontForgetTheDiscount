package su.tease.dontforgetthediscount

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import su.tease.core.mvi.android.AndroidMviApplication
import su.tease.dontforgetthediscount.database.databaseModule
import su.tease.dontforgetthediscount.module.dontForgetTheDiscountModule
import su.tease.dontforgetthediscount.network.retrofitModule
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountLogger
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountMiddlewares
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountReducer
import su.tease.feature.splash.splashModule
import su.tease.project.feature.cacheback.module.cacheBackModule
import su.tease.project.feature.main.module.mainModule
import timber.log.Timber

class DontForgetTheDiscountApplication : AndroidMviApplication(
    dontForgetTheDiscountReducer,
    dontForgetTheDiscountMiddlewares,
    dontForgetTheDiscountLogger,
) {
    private val dontForgetTheDiscountStoreModule = module { factory { store } }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@DontForgetTheDiscountApplication)
            modules(dontForgetTheDiscountStoreModule)
            modules(dontForgetTheDiscountModule)
            modules(databaseModule)
            modules(retrofitModule)
            modules(splashModule)
            modules(mainModule)
            modules(cacheBackModule)
        }
    }
}
