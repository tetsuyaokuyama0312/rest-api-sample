package com.example.restapi.dto.resource

import com.example.restapi.dto.model.User
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDate
import java.util.*

open class UserResource(
    var uid: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var birthday: LocalDate? = null
) : Serializable {
    companion object {
        fun fromUser(user: User): UserResource {
            return UserResource(user.uid, user.lastName, user.firstName, user.birthday)
        }
    }

    fun toUser(): User {
        return User(
            uid ?: UUID.randomUUID().toString(),
            lastName, firstName, birthday ?: LocalDate.now()
        )
    }
}