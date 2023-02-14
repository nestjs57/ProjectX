package com.arnoract.projectx.common.time

import java.util.*

interface TimeProvider {
    fun now(): Long
    fun getCurrentTimeZone(): TimeZone
}
