package ru.andreym.marveltask.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.andreym.marveltask.BuildConfig
import ru.andreym.marveltask.repository.network.MarvelApi
import ru.andreym.marveltask.utils.toMD5

val networkModule = Kodein.Module("NetworkModule") {

    bind<OkHttpClient>() with singleton {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(getAuthInterceptor())
            .addInterceptor(logging)
            .build()
    }

    bind<Retrofit>() with singleton { getRetrofitInstance(instance()) }
    bind<MarvelApi>() with singleton { getMarvelAppService(instance()) }
}


private fun getAuthInterceptor() =
    Interceptor {
        val request = it.request()

        val timestamp = System.currentTimeMillis()
        val hash = "$timestamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".toMD5()
        val url = request.url().newBuilder()
            .addQueryParameter("ts", timestamp.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
            .addQueryParameter("hash", hash)
            .build()
        val newRequest = request.newBuilder().url(url).build()
        it.proceed(newRequest)
    }

private fun getRetrofitInstance(client: OkHttpClient) =

    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

private fun getMarvelAppService(retrofit: Retrofit) =
    retrofit.create(MarvelApi::class.java)