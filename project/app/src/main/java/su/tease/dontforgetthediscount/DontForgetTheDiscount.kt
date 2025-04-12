package su.tease.dontforgetthediscount

import android.app.Application
import org.koin.core.context.startKoin
import su.tease.dontforgetthediscount.di.dontForgetTheDiscountModule

class DontForgetTheDiscount: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(dontForgetTheDiscountModule)
        }
    }
}
