package com.bukalapak.hero.services

import com.bukalapak.hero.models.Role
import com.bukalapak.hero.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.persistence.EntityNotFoundException

@Service
class RoleService {
  @Autowired
  RoleRepository roleRepository

  Role findByIdOrError(String id) {
    roleRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }
}
