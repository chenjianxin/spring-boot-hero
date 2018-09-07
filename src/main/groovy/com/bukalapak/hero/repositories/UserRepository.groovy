package com.bukalapak.hero.repositories

import com.bukalapak.hero.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository extends CrudRepository<User, String> {
}
