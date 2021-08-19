package com.example.restapi.service.impl

import com.example.restapi.dto.criteria.UserCriteria
import com.example.restapi.dto.model.User
import com.example.restapi.service.UserService
import com.example.restapi.util.readListFromJsonResource
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Service
class UserServiceImpl : UserService {
    companion object {
        private const val DUMMY_USERS_FILE_NAME = "/data/dummy_users.json"
    }

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val userRepository: MutableMap<String, User> = ConcurrentHashMap()

    @PostConstruct
    fun loadDummyData() {
        val dummyUsers = readListFromJsonResource(DUMMY_USERS_FILE_NAME, User::class.java, mapper, this)
        dummyUsers.forEach {
            userRepository[it.uid] = it
        }
    }

    override fun find(uid: String): User? {
        return userRepository[uid]
    }

    override fun findAll(): List<User> {
        return ArrayList(userRepository.values)
    }

    override fun findAllByCriteria(userCriteria: UserCriteria): List<User> {
        return userRepository.values
            .filter { userCriteria.matches(it) }
            .sortedBy { it.uid }
    }

    override fun register(user: User) {
        userRepository[user.uid] = user
    }

    override fun update(user: User) {
        userRepository[user.uid] = user
    }

    override fun delete(uid: String) {
        userRepository.remove(uid)
    }
}