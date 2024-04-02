package ru.tyurin.foodies.data.repository

import ru.tyurin.foodies.data.network.CategoryService
import ru.tyurin.foodies.data.network.ProductService
import ru.tyurin.foodies.data.network.TagService
import ru.tyurin.foodies.domain.models.Category
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.domain.models.Tag
import ru.tyurin.foodies.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val tagService: TagService,
    private val productService: ProductService
) : Repository {

    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories()
    }

    override suspend fun getTags(): List<Tag> {
        return tagService.getTags()
    }

    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }
}