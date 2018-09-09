package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.User
import com.bukalapak.hero.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('user')
@Transactional
// everyone who has logged in can access, unless specified otherwise
@PreAuthorize('isAuthenticated()')
class UserController {
  @Autowired
  UserService userService

  @GetMapping
  // filter results to only show current user object, unless they are admins
  @PostFilter('hasRole("ROLE_ADMIN") or filterObject.username == principal.username')
  List<User> findAll() {
    userService.findAll()
  }

  @GetMapping('{id}')
  // only accessable to admins or the current user object
  @PreAuthorize('hasRole("ROLE_ADMIN") or #id == principal.username')
  User findById(@PathVariable String id) {
    userService.findById(id)
  }

  @PostMapping
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  User create(@RequestBody User user) {
    userService.create(user)
  }

  @PutMapping('{id}/password')
  // make sure users can only change their own passwords, unless they are admins
  @PreAuthorize('hasRole("ROLE_ADMIN") or #id == principal.username')
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
  // prevent admin from deleting himself
  @PreAuthorize('hasRole("ROLE_ADMIN") and #id != "admin"')
  User deleteById(String id) {
    userService.deleteById(id)
  }
}