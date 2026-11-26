package com.example.data.repository

import com.example.data.local.DracuversusCatalogSeed
import com.example.data.local.FigureDao
import com.example.data.model.FigureEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class MuseumRepository(private val figureDao: FigureDao) {

    private val seedMutex = Mutex()

    fun observeAllFigures(): Flow<List<FigureEntity>> = figureDao.observeAllFigures()

    fun observeFigureById(id: String): Flow<FigureEntity?> = figureDao.observeFigureById(id)

    suspend fun ensureSeeded() = withContext(Dispatchers.IO) {
        seedMutex.withLock {
            val expectedEntries = DracuversusCatalogSeed.buildAllEntries()
            figureDao.insertMissing(expectedEntries)
        }
    }

    suspend fun updateFigurePhoto(id: String, localPath: String?) = withContext(Dispatchers.IO) {
        val status = if (localPath.isNullOrBlank()) "PENDIENTE_ARCHIVO" else "DISPONIBLE"
        figureDao.updatePhoto(id = id, photoPath = localPath, photoStatus = status)
    }
}
