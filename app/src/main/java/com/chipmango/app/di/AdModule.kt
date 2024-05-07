package com.chipmango.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.chipmango.ad.di.AdTestDeviceList
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdModule {

    @AdTestDeviceList
    @Singleton
    @Provides
    fun provideAdTestDeviceList() : List<String> {
        return listOf(
            "882606C5CFD6E4F88B829F7D646525E1"
        )
    }
}