package com.example.restapi.service

import com.example.restapi.dto.criteria.UserCriteria
import com.example.restapi.dto.model.User

interface UserService {
    fun find(uid: String): User?

    fun findAll(): List<User>

    fun findAllByCriteria(criteria: UserCriteria): List<User>

    fun register(user: User)

    fun update(user: User)

    fun delete(uid: String)
}