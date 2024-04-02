package ru.tyurin.foodies.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tyurin.foodies.data.network.CategoryService
import ru.tyurin.foodies.data.network.ProductService
import ru.tyurin.foodies.data.network.TagService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://anika1d.github.io/WorkTestServer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    fun provideTagService(retrofit: Retrofit): TagService {
        return retrofit.create(TagService::class.java)
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }
}