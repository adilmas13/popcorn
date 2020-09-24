package com.popcorn.domain.exceptions

class ApiException(
    error: String?
) : Exception(error)
