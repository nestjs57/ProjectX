package com.arnoract.projectx.base

open class BusinessException : RuntimeException {
    constructor() : super()
    constructor(cause: String) : super(cause)
}