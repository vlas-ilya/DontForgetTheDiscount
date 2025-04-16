package su.tease.dontforgetthediscount

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import su.tease.core.mvi.android.AndroidMviApplication
import su.tease.dontforgetthediscount.module.dontForgetTheDiscountModule
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountMiddlewares
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountReducer
import su.tease.feature.splash.splashModule
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.module.cacheBackModule
import su.tease.project.feature.main.module.mainModule

class DontForgetTheDiscountApplication : AndroidMviApplication(
    dontForgetTheDiscountReducer,
    dontForgetTheDiscountMiddlewares,
) {
    private val dontForgetTheDiscountStoreModule = module {
        factory<Store<*>> { store }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DontForgetTheDiscountApplication)
            modules(dontForgetTheDiscountStoreModule)
            modules(splashModule)
            modules(mainModule)
            modules(cacheBackModule)
            modules(dontForgetTheDiscountModule)
        }
    }
}
