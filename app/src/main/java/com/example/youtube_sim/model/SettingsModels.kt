package com.example.youtube_sim.model

data class LanguageOption(
    val key: String,
    val label: String,
    val nativeLabel: String,
    val note: String? = null
)

data class QualityOption(
    val key: String,
    val label: String,
    val description: String
)

data class QualityPreferenceSection(
    val key: String,
    val title: String,
    val options: List<QualityOption>
)
