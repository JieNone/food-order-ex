package ru.tyurin.foodies.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyurin.foodies.data.repository.RepositoryImpl
import ru.tyurin.foodies.domain.repository.Repository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository {
        return repositoryImpl
    }
}