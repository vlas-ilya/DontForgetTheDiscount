package su.tease.dontforgetthediscount.network

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val retrofitModule = module {
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) } bind Interceptor::class

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .apply { getAll<Interceptor>().forEach(::addInterceptor) }
            .build()
    }

    single<Retrofit> {
        val baseUrl = "https://dontforgetthediscount.ru/"
        val okHttpClient = get<OkHttpClient>()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
