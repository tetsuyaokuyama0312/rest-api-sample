package com.example.restapi.dto.criteria

abstract class EntityCriteriaBase<T> : EntityCriteria<T> {
    protected open fun isApplicable(property: Any?): Boolean {
        if (property == null) {
            return false
        }
        if (property is String && property.isNullOrEmpty()) {
            return false
        }
        return true
    }
}