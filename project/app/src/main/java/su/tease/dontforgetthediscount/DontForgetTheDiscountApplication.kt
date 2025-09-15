package su.tease.dontforgetthediscount

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import su.tease.core.mvi.android.AndroidMviApplication
import su.tease.dontforgetthediscount.database.databaseModule
import su.tease.dontforgetthediscount.module.dontForgetTheDiscountModule
import su.tease.dontforgetthediscount.network.retrofitModule
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountInterceptors
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountLogger
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountMiddlewares
import su.tease.dontforgetthediscount.state.dontForgetTheDiscountReducer
import su.tease.feature.splash.splashModule
import su.tease.project.feature.bank.integration.module.bankIntegrationModule
import su.tease.project.feature.cashback.integration.module.cashBackIntegrationModule
import su.tease.project.feature.info.integration.module.otherModule
import su.tease.project.feature.main.module.mainModule
import su.tease.project.feature.preset.integration.module.presetIntegrationModule
import su.tease.project.feature.shop.integration.module.shopIntegrationModule
import timber.log.Timber

class DontForgetTheDiscountApplication : AndroidMviApplication(
    dontForgetTheDiscountReducer,
    dontForgetTheDiscountInterceptors,
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

            modules(presetIntegrationModule)
            modules(bankIntegrationModule)
            modules(cashBackIntegrationModule)
            modules(shopIntegrationModule)

            modules(splashModule)
            modules(mainModule)
            modules(otherModule)
        }

        println()
    }
}
