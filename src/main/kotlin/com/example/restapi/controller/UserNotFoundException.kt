package com.example.restapi.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(uid: String) : RuntimeException("User is not found (uid = $uid)")