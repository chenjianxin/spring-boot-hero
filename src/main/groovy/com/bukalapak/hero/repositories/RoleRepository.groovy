package com.bukalapak.hero.repositories

import com.bukalapak.hero.models.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository extends CrudRepository<Role, String> {
}