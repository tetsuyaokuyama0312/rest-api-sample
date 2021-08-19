package com.example.restapi.dto.criteria

import java.io.Serializable

interface EntityCriteria<T> : Serializable {
    fun matches(entity: T): Boolean
}