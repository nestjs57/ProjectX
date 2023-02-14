package com.arnoract.projectx.core

interface Mapper<in From, out To> {
    fun map(from: From): To
}