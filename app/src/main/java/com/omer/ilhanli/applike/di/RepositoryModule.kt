package com.omer.ilhanli.applike.di

import com.omer.ilhanli.applike.data.repository.SatelliteRepository
import com.omer.ilhanli.applike.data.repository.SatelliteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSatelliteRepository(satelliteRepository: SatelliteRepositoryImpl): SatelliteRepository
}