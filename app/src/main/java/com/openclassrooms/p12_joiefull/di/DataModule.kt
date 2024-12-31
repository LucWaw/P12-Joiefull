package com.openclassrooms.p12_joiefull.di

import com.openclassrooms.p12_joiefull.data.network.ClothingApi
import com.openclassrooms.p12_joiefull.data.repository.ClothingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideClothingRepository(dataClient: ClothingApi): ClothingRepository {
        return ClothingRepository(dataClient)
    }
}
