package com.crosstime.backend.model

data class Exercise(
    val id: Long,
    val name: String,
    val forceType: String?,
    val level: String,
    val mechanic: String?,
    val equipment: String?,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
    val category: String,
    val images: List<String>,
    val keyName: String
)
