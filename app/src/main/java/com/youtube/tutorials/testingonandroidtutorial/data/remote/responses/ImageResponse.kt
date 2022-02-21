package com.youtube.tutorials.testingonandroidtutorial.data.remote.responses

data class ImageResponse(
    val hints: List<ImageResult>,
    val total: Int,
    val totalHints: Int
)
