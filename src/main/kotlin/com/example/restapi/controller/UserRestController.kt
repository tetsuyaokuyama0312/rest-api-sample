package com.example.restapi.controller

import com.example.restapi.dto.criteria.UserCriteria
import com.example.restapi.dto.query.UserResourceQuery
import com.example.restapi.dto.resource.UserResource
import com.example.restapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate


/**
 * REST API用コントローラ
 */
@RestController
@RequestMapping("user")
class UserRestController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("{uid}")
    fun findUser(@PathVariable uid: String): UserResource {
        val user = userService.find(uid) ?: throw UserNotFoundException(uid)
        return UserResource.fromUser(user)
    }

    @GetMapping
    fun searchUsers(@Validated query: UserResourceQuery): List<UserResource> {
        val criteria = UserCriteria()
        criteria.lastName = query.lastName
        criteria.firstName = query.firstName
        criteria.birthday = query.birthday?.let { LocalDate.parse(it) }

        val users = userService.findAllByCriteria(criteria)
        return users.map { UserResource.fromUser(it) }
    }

    @PostMapping
    fun registerUser(
        @Validated @RequestBody newResource: UserResource,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        val user = newResource.toUser()
        userService.register(user)

        val resUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
            .withMethodCall(on(UserRestController::class.java).findUser(user.uid))
            .build().encode().toUri()
        return ResponseEntity.created(resUri).build()
    }

    @PutMapping("{uid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@PathVariable uid: String, @Validated @RequestBody newResource: UserResource) {
        val newUser = newResource.toUser()
        userService.update(newUser)
    }

    @DeleteMapping("{uid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable uid: String) {
        userService.delete(uid)
    }
}