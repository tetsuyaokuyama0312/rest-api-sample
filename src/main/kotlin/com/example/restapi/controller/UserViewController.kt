package com.example.restapi.controller

import com.example.restapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


/**
 * View表示用コントローラ
 *
 * REST APIなので本来はViewは不要だが、利便性を考慮して画面を作成する
 */
@Controller
class UserViewController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("users")
    fun userView(model: Model): String {
        val users = userService.findAll().sortedWith(
            compareBy({ it.lastName }, { it.firstName })
        )
        model.addAttribute("users", users)
        return "userView"
    }
}