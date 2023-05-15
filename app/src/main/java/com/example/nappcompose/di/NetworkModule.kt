package com.example.nappcompose.di

import com.example.nappcompose.BuildConfig
import com.example.nappcompose.data.remote.GhUserDataSource
import com.example.nappcompose.data.remote.UserApi
import com.example.nappcompose.data.remote.UserDataStore
import com.example.nappcompose.domain.repository.DataRepository
import com.example.nappcompose.domain.repository.DataRepositoryImpl
import com.example.nappcompose.util.decryptCBC
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindsDataStore(repository: GhUserDataSource): UserDataStore

    @Binds
    abstract fun bindsDataRepository(repository: DataRepositoryImpl): DataRepository

    companion object {
        @Provides
        @Singleton
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
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
            return HttpLoggingInterceptor.Logger { message ->
                Timber.d("HTTP::Service:: $message")
            }
        }

        @Provides
        fun provideLoggingInterceptor(
            httpLogger: HttpLoggingInterceptor.Logger
        ): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
            loggingInterceptor.level =
                HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }

        @Provides
        @Named("headers")
        fun provideHeadersInterceptor() = Interceptor { chain ->
            var request = chain.request()
            val decrTkn = "Bearer ${BuildConfig.TKN.decryptCBC()}"
            request = request.newBuilder()
                .addHeader("Authorization", decrTkn)
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()

            chain.proceed(request)
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            @Named("headers") headersInterceptor: Interceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(headersInterceptor)
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

        private const val BASE_URL = "https://api.github.com/"
    }
}
