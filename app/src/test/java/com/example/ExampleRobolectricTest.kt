package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.audio.MuseumAudioNarrator
import com.example.data.local.DracuversusCatalogSeed
import com.example.data.model.EraPeriod
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `app name is Dracuversus`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("Dracuversus", context.getString(R.string.app_name))
    }

    @Test
    fun `seed catalog has unique ids and spans 1989 to 2026 across all four eras`() {
        val entries = DracuversusCatalogSeed.buildAllEntries()
        assertTrue("Catalog should contain over 300 entries", entries.size >= 300)

        val uniqueIds = entries.map { it.id }.toSet()
        assertEquals("All entry IDs must be unique", entries.size, uniqueIds.size)

        val erasPresent = entries.map { it.era }.toSet()
        assertEquals(EraPeriod.entries.map { it.code }.toSet(), erasPresent)

        assertTrue(entries.any { it.year == 1989 })
        assertTrue(entries.any { it.year == 2026 })
    }

    @Test
    fun `audio narrator splits Spanish text into clean segments`() {
        val segments = MuseumAudioNarrator.splitIntoNarrationSegments(
            "En octubre de 1989 nació la Paleta Drácula. Fue un éxito en Halloween.\nIncluyó colmillos neón."
        )
        assertEquals(3, segments.size)
        assertEquals("En octubre de 1989 nació la Paleta Drácula.", segments[0])
    }

    @Test
    fun `user can enter museum search figure open detail and open reading modal`() {
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("enter_museum_button").assertIsDisplayed().performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("search_input").assertIsDisplayed().performTextInput("Calyxor")
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("figure_card_DRAC-2026-014").assertIsDisplayed().performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithText("Secretos del Pasillo Mutante").assertIsDisplayed()
        composeRule.onNodeWithTag("read_history_button").assertIsDisplayed().performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("reading_modal").assertIsDisplayed()
        composeRule.onNodeWithTag("close_reading_button").performClick()
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("listen_history_button").assertIsDisplayed()
        composeRule.onNodeWithTag("back_to_catalog_button").performClick()
        composeRule.waitForIdle()
    }
}
