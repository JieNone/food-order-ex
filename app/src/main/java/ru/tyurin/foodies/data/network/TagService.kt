package ru.tyurin.foodies.data.network

import retrofit2.http.GET
import ru.tyurin.foodies.domain.models.Tag

interface TagService {
    @GET("Tags.json")
    suspend fun getTags(): List<Tag>
}