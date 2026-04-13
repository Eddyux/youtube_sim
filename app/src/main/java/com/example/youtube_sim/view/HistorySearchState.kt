package com.example.youtube_sim.view

internal data class HistorySearchState(
    val draftQuery: String,
    val submittedQuery: String,
    val appliedQuery: String,
    val hasSubmittedSearch: Boolean,
    val hasPendingSearch: Boolean
)

internal fun resolveHistorySearchState(
    draftQuery: String,
    submittedQuery: String
): HistorySearchState {
    val normalizedDraft = draftQuery.trim()
    val normalizedSubmitted = submittedQuery.trim()
    val hasSubmittedSearch = normalizedSubmitted.isNotBlank() && normalizedDraft == normalizedSubmitted

    return HistorySearchState(
        draftQuery = normalizedDraft,
        submittedQuery = normalizedSubmitted,
        appliedQuery = if (hasSubmittedSearch) normalizedSubmitted else "",
        hasSubmittedSearch = hasSubmittedSearch,
        hasPendingSearch = normalizedDraft.isNotBlank() && !hasSubmittedSearch
    )
}
