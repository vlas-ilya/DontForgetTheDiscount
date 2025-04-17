package su.tease.project.core.utils.utils

import org.koin.core.module.Module
import retrofit2.Retrofit

inline fun <reified T> Module.api() {
    single<T> {
        val retrofit = get<Retrofit>()
        retrofit.create(T::class.java)
    }
}
