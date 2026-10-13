package com.aicaller.app.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDestinationTest {
    @Test
    fun destinationsHaveExpectedOrderAndLabels() {
        assertEquals(listOf("Home", "Contacts", "History", "AI", "Settings"), AppDestination.entries.map { it.label })
    }

    @Test
    fun everyDestinationHasAnIconKey() {
        assertTrue(AppDestination.entries.all { it.icon.isNotBlank() })
    }
}
