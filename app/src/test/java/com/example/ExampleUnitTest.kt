package com.example

import com.example.audio.MuseumAudioNarrator
import com.example.data.local.DracuversusCatalogSeed
import com.example.data.model.EraPeriod
import com.example.data.model.VerificationStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `catalog seed contains all 322 authentic entries with unique IDs`() {
        val entries = DracuversusCatalogSeed.buildAllEntries()
        assertEquals("Expected all 322 catalog entries preserved", 322, entries.size)

        val uniqueIds = entries.map { it.id }.toSet()
        assertEquals("Every entry must have a unique ID", entries.size, uniqueIds.size)
    }

    @Test
    fun `catalog covers 1989 to 2026 across all four eras in chronological sort order`() {
        val entries = DracuversusCatalogSeed.buildAllEntries()

        val erasPresent = entries.map { it.era }.toSet()
        assertEquals(EraPeriod.entries.map { it.code }.toSet(), erasPresent)

        assertTrue(entries.any { it.year == 1989 })
        assertTrue(entries.any { it.year == 1994 })
        assertTrue(entries.any { it.year == 2000 })
        assertTrue(entries.any { it.year == 2012 })
        assertTrue(entries.any { it.year == 2019 })
        assertTrue(entries.any { it.year == 2026 })

        val sortOrders = entries.map { it.sortOrder }
        assertEquals(sortOrders.sorted(), sortOrders)
    }

    @Test
    fun `every entry has non-empty Spanish history and starts with pending photo`() {
        val entries = DracuversusCatalogSeed.buildAllEntries()
        val validStatuses = VerificationStatus.entries.map { it.code }.toSet()

        entries.forEach { figure ->
            assertFalse("Name blank for ${figure.id}", figure.name.isBlank())
            assertFalse("Collection blank for ${figure.id}", figure.collectionName.isBlank())
            assertFalse("History blank for ${figure.id}", figure.history.isBlank())
            assertTrue("Invalid verification status for ${figure.id}", figure.verificationStatus in validStatuses)
            assertEquals("Initial photoAssetPath must be null for ${figure.id}", null, figure.photoAssetPath)
            assertEquals("PENDIENTE_ARCHIVO", figure.photoStatus)
        }
    }

    @Test
    fun `audio narrator segments Spanish paragraphs properly`() {
        val text = "En octubre de 1989 nació la Paleta Drácula. Fue un éxito en Halloween.\nIncluyó colmillos neón."
        val segments = MuseumAudioNarrator.splitIntoNarrationSegments(text)
        assertEquals(3, segments.size)
        assertEquals("En octubre de 1989 nació la Paleta Drácula.", segments[0])
        assertEquals("Fue un éxito en Halloween.", segments[1])
        assertEquals("Incluyó colmillos neón.", segments[2])
    }
}
