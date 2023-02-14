package com.arnoract.projectx.domain.main

import com.arnoract.projectx.domain.model.main.Station

interface StationRepository {
    suspend fun getStations(): MutableList<Station>
}