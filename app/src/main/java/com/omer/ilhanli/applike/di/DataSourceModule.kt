package com.omer.ilhanli.applike.di

import com.omer.ilhanli.applike.data.source.local.LocalSatelliteDataSource
import com.omer.ilhanli.applike.data.source.remote.RemoteSatelliteDataSource
import com.omer.ilhanli.applike.data.source.SatelliteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @RemoteDataSource
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteSatelliteDataSource): SatelliteDataSource

    @Binds
    @LocalDataSource
    abstract fun provideLocalDataSource(localDataSource: LocalSatelliteDataSource): SatelliteDataSource
}