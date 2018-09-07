package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.User
import com.bukalapak.hero.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('user')
@Transactional
class UserController {
  @Autowired
  UserService userService

  @GetMapping
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  List<User> findAll() {
    userService.findAll()
  }

  @GetMapping('{id}')
  @PreAuthorize('isAuthenticated()')
  User findById(@PathVariable String id) {
    userService.findById(id)
  }

  @PostMapping
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  User create(@RequestBody User user) {
    userService.create(user)
  }

  @PutMapping('{id}/password')
  @PreAuthorize('isAuthenticated()')
  User updatePassword(
      @PathVariable String id,
      @RequestBody(required = true) String password) {
    userService.updatePassword(id, password)
  }

  @PutMapping('{id}/enabled')
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  User updateEnabled(
      @PathVariable String id,
      @RequestBody(required = true) boolean enabled) {
    userService.updateEnabled(id, enabled)
  }

  @DeleteMapping('{id}')
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  User deleteById(String id) {
    userService.deleteById(id)
  }
}
