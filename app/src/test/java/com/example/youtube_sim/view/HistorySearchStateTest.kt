package com.example.youtube_sim.view

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HistorySearchStateTest {

    @Test
    fun resolveHistorySearchState_keepsResultsHiddenUntilSearchIsSubmitted() {
        val state = resolveHistorySearchState(
            draftQuery = "iphone",
            submittedQuery = ""
        )

        assertEquals("iphone", state.draftQuery)
        assertEquals("", state.appliedQuery)
        assertTrue(state.hasPendingSearch)
        assertFalse(state.hasSubmittedSearch)
    }

    @Test
    fun resolveHistorySearchState_appliesQueryAfterSearchSubmission() {
        val state = resolveHistorySearchState(
            draftQuery = "iphone",
            submittedQuery = "iphone"
        )

        assertEquals("iphone", state.appliedQuery)
        assertTrue(state.hasSubmittedSearch)
        assertFalse(state.hasPendingSearch)
    }

    @Test
    fun resolveHistorySearchState_clearsAppliedQueryWhenDraftChanges() {
        val state = resolveHistorySearchState(
            draftQuery = "iphone 16",
            submittedQuery = "iphone"
        )

        assertEquals("", state.appliedQuery)
        assertTrue(state.hasPendingSearch)
        assertFalse(state.hasSubmittedSearch)
    }
}
