package com.example.youtube_sim.data

import com.example.youtube_sim.model.HomeTabContent

interface HomeFeedRepository {
    fun loadTabs(): List<HomeTabContent>
}
