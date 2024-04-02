package ru.tyurin.foodies.data.network

import retrofit2.http.GET
import ru.tyurin.foodies.domain.models.Product

interface ProductService {
    @GET("Products.json")
    suspend fun getProducts(): List<Product>
}