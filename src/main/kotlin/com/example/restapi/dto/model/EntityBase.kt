package com.example.restapi.dto.model

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible


open class EntityBase {
    private val propertyOrderMap = mutableMapOf<String, Int>()

    init {
        javaClass.kotlin.primaryConstructor
            ?.let {
                it.parameters.mapNotNull { p -> p.name }
                    ?.forEachIndexed { i, name ->
                        propertyOrderMap[name] = i
                    }
            } ?: javaClass.kotlin.memberProperties.forEachIndexed { i, p ->
            propertyOrderMap[p.name] = i
        }
    }

    override fun toString(): String {
        return javaClass.kotlin.memberProperties
            .sortedBy { propertyOrderMap.getOrDefault(it.name, Int.MAX_VALUE) }
            .mapNotNull(::toDisplayString)
            .joinToString(" / ")
    }

    private fun toDisplayString(property: KProperty1<EntityBase, *>): String? {
        property.isAccessible = true
        return property.get(this)?.let {
            "${property.name}: $it"
        }
    }
}