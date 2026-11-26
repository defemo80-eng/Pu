package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.FigureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FigureDao {

    @Query("SELECT * FROM figures ORDER BY sortOrder ASC")
    fun observeAllFigures(): Flow<List<FigureEntity>>

    @Query("SELECT * FROM figures WHERE id = :id LIMIT 1")
    fun observeFigureById(id: String): Flow<FigureEntity?>

    @Query("SELECT COUNT(*) FROM figures")
    suspend fun countFigures(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMissing(figures: List<FigureEntity>)

    @Query("UPDATE figures SET photoAssetPath = :photoPath, photoStatus = :photoStatus WHERE id = :id")
    suspend fun updatePhoto(id: String, photoPath: String?, photoStatus: String)
}
