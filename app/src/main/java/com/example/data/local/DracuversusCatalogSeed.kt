package com.example.data.local

import com.example.data.model.FigureEntity

object DracuversusCatalogSeed {

    fun buildAllEntries(): List<FigureEntity> {
        val early = DracuversusSeed1989.entries()
        val twoThousands = DracuversusSeed2000.entries(startOrder = early.size + 1)
        val twentyTens = DracuversusSeed2010.entries(startOrder = early.size + twoThousands.size + 1)
        val twentyTwenties = DracuversusSeed2020.entries(
            startOrder = early.size + twoThousands.size + twentyTens.size + 1
        )
        return early + twoThousands + twentyTens + twentyTwenties
    }
}
