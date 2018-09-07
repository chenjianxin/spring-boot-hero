package com.bukalapak.hero.services

import com.bukalapak.hero.models.User
import com.bukalapak.hero.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import javax.persistence.EntityNotFoundException

@Service
class UserService {
  @Autowired
  UserRepository userRepository

  @Autowired
  PasswordEncoder passwordEncoder

  List<User> findAll() {
    userRepository.findAll().asList()
  }

  User findById(String id) {
    userRepository.findById(id).orElse(null)
  }

  User findByIdOrError(String id) {
    userRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }

  User create(User user) {
    user.password = passwordEncoder.encode(user.password)
    userRepository.save(user)
  }

  User updatePassword(String id, String password) {
    def user = findByIdOrError(id)
    user.password = passwordEncoder.encode(password)
    userRepository.save(user)
  }

  User updateEnabled(String id, boolean enabled) {
    def user = findByIdOrError(id)
    user.enabled = enabled
    userRepository.save(user)
  }

  User deleteById(String id) {
    def user = findByIdOrError(id)
    userRepository.delete(user)
    user
  }


}
