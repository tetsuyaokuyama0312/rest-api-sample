package com.example.restapi.dto.query

import java.io.Serializable

class UserResourceQuery(
    var lastName: String? = null,
    var firstName: String? = null,
    var birthday: String? = null
) : Serializable