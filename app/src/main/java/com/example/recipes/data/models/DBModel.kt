package com.example.recipes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe")
data class DBModel (
    @PrimaryKey(autoGenerate = true)
var idDb: Int? = null,
    val aggregateLikes: Int,
    val cookingMinutes: Int,
    val id: Int,
    val image: String,
    val instructions: String,
    val preparationMinutes: Int,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    var isLike: Boolean
): java.io.Serializable
