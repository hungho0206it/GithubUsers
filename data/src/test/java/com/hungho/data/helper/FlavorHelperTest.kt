package com.hungho.data.helper

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FlavorHelperTest {
    @Test
    fun `isDevMode returns true when flavor is dev`() {
        // Given
        val mockProvider = mockk<FlavorProvider>()
        every { mockProvider.getFlavor() } returns "dev"
        val helper = FlavorHelper(mockProvider)
        //When
        val result = helper.isDevMode()

        // Then
        assertTrue(result)
    }

    @Test
    fun `isProdMode returns true when flavor is prod`() {
        // Given
        val mockProvider = mockk<FlavorProvider>()
        every { mockProvider.getFlavor() } returns "prod"
        val helper = FlavorHelper(mockProvider)

        //When
        val result = helper.isProdMode()

        // Then
        assertTrue(result)
    }
}