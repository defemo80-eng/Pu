package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "figures")
data class FigureEntity(
    @PrimaryKey val id: String,
    val name: String,
    val year: Int,
    val yearLabel: String,
    val era: String,
    val collectionName: String,
    val subGroup: String,
    val photoAssetPath: String?,
    val photoStatus: String,
    val shortDescription: String,
    val history: String,
    val curiosities: String,
    val verificationStatus: String,
    val sourceNote: String,
    val sortOrder: Int
)
