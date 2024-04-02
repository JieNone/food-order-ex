package ru.tyurin.foodies.domain.repository

import ru.tyurin.foodies.domain.models.Category
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.domain.models.Tag

interface Repository {
    suspend fun getCategories(): List<Category>
    suspend fun getTags(): List<Tag>
    suspend fun getProducts(): List<Product>
}