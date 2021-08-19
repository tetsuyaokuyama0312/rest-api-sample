package com.example.restapi.dto.criteria

import com.example.restapi.dto.model.User
import java.time.LocalDate

class UserCriteria(
    var lastName: String? = null,
    var firstName: String? = null,
    var birthday: LocalDate? = null
) : EntityCriteriaBase<User>() {

    override fun matches(entity: User): Boolean {
        if (isApplicable(lastName) && (entity.lastName.isNullOrEmpty() || !entity.lastName!!.contains(lastName!!))) {
            return false
        }
        if (isApplicable(firstName) && (entity.firstName.isNullOrEmpty() || !entity.firstName!!.contains(firstName!!))) {
            return false
        }
        if (isApplicable(birthday) && birthday != entity.birthday) {
            return false
        }
        return true
    }
}