package com.example.catapp.utils

object ImageUrlUtils {
    private const val BASE_IMAGE_URL = "https://cdn2.thecatapi.com/images/"

    fun buildImageUrl(path: String): String {
        return "$BASE_IMAGE_URL$path.jpg"
    }
}
