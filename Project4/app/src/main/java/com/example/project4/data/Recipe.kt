package com.example.project4.data

data class Recipe(
    val id: Int,
    val name: String,
    val category: String,
    val ingredients: List<IngredientMeasurement>,
    val instructions: String,
    val imageUrl: String,
    val youtubeVideo: String?
)