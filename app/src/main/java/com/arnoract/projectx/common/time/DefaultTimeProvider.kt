package com.arnoract.projectx.common.time

import java.util.*

class DefaultTimeProvider : TimeProvider {
    override fun now(): Long {
        return Date().time
    }

    override fun getCurrentTimeZone(): TimeZone {
        return TimeZone.getDefault()
    }
}
