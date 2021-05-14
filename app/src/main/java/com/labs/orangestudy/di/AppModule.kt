package com.labs.orangestudy.di

import com.labs.orangestudy.data.api.TvApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Provides
        fun provideLoggingInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        fun provideOkHttpClient (logger: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

        @Provides
        fun provideRetrofit(client : OkHttpClient) : Retrofit = Retrofit.Builder()
            .baseUrl(TvApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        @Provides
        fun provideTvApi(retrofit: Retrofit) : TvApi = retrofit.create(TvApi::class.java)

}