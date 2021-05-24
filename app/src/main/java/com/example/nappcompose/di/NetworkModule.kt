package com.example.nappcompose.di

import com.example.nappcompose.data.BtcDataStore
import com.example.nappcompose.data.remote.BtcApi
import com.example.nappcompose.data.remote.RemoteBtcDataSource
import com.example.nappcompose.repository.DataRepository
import com.example.nappcompose.repository.DataRepositoryImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideBtcDataStore(api: BtcApi): BtcDataStore =
        RemoteBtcDataSource(api)

    @Provides
    fun provideDataRepository(dataSource: RemoteBtcDataSource): DataRepository =
        DataRepositoryImpl(dataSource)
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://api.blockchain.info/"

    @Provides
    @Singleton
    fun provideBtcApi(retrofit: Retrofit): BtcApi {
        return retrofit.create(BtcApi::class.java)
    }

    @Provides
    fun provideSerializer(): Gson {
        return GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor.Logger {
        Timber.plant(Timber.DebugTree())
        return object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d("HTTP::Service:: $message")
            }
        }
    }

    @Provides
    fun provideLoggingInterceptor(
        httpLogger: HttpLoggingInterceptor.Logger
    ): HttpLoggingInterceptor {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return interceptor
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
