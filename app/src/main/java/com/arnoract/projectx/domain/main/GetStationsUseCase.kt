package com.arnoract.projectx.domain.main

import com.arnoract.projectx.domain.model.main.Station
import com.arnoract.projectx.core.UseCase

class GetStationsUseCase(
    private val stationRepository: StationRepository
) : UseCase<Unit, MutableList<Station>>() {

    override suspend fun execute(parameters: Unit): MutableList<Station> {
        return stationRepository.getStations()
    }
}