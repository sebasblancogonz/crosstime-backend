package com.crosstime.backend.entity

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "exercises")
data class Exercise(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val forceType: String?,
    val level: String,
    val mechanic: String?,
    val equipment: String?,
    @Column(name = "primary_muscles", columnDefinition = "json")
    var primaryMusclesJson: String? = null,
    @Column(name = "secondary_muscles", columnDefinition = "json")
    var secondaryMusclesJson: String? = null,
    @Column(name = "instructions", columnDefinition = "json")
    var instructionsJson: String? = null,
    val category: String,
    @Column(name = "images", columnDefinition = "json")
    var imagesJson: String? = null,
    val keyName: String
) {
    val primaryMuscles: List<String>
        get() = deserializeJsonArray(primaryMusclesJson)

    val secondaryMuscles: List<String>
        get() = deserializeJsonArray(secondaryMusclesJson)

    val instructions: List<String>
        get() = deserializeJsonArray(instructionsJson)

    val images: List<String>
        get() = deserializeJsonArray(imagesJson)

    private fun deserializeJsonArray(jsonArray: String?): List<String> {
        return ObjectMapper().readValue<List<String>>(jsonArray ?: "[]")
    }

}
