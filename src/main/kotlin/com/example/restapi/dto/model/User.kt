package com.example.restapi.dto.model

import java.time.LocalDate
import java.util.*

class User(
    val uid: String = UUID.randomUUID().toString(),
    var lastName: String?,
    var firstName: String?,
    var birthday: LocalDate = LocalDate.now()
) : EntityBase() {
    constructor() : this(
        lastName = null,
        firstName = null
    )
}