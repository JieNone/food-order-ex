package ru.tyurin.foodies.data.network

import retrofit2.http.GET
import ru.tyurin.foodies.domain.models.Category

interface CategoryService {
    @GET("Categories.json")
    suspend fun getCategories(): List<Category>
}