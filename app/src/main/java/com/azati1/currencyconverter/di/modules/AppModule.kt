package com.azati1.currencyconverter.di.modules

import com.azati1.currencyconverter.data.CurrenciesApi
import com.azati1.currencyconverter.interactor.CurrenciesInteractor
import com.azati1.currencyconverter.interactor.CurrenciesInteractorImpl
import com.azati1.currencyconverter.model.CurrencyDataProvider
import com.azati1.currencyconverter.model.CurrencyDataProviderImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun provideApi() : CurrenciesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CurrenciesApi::class.java)
    }

    @Provides
    fun provideDataProvider(api : CurrenciesApi) : CurrencyDataProvider {
        return CurrencyDataProviderImpl(api)
    }

    @Provides
    fun provideInteractor(repository : CurrencyDataProvider) : CurrenciesInteractor {
        return CurrenciesInteractorImpl(repository)
    }

}