package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.Hero
import com.bukalapak.hero.services.HeroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('hero')
@Transactional
// anyone who has logged in can access, unless specified otherwise
@PreAuthorize('isAuthenticated()')
class HeroController {
  @Autowired
  HeroService heroService

  @GetMapping('')
  List findAll() {
    heroService.findAll()
  }

  @GetMapping('{id}')
  Hero findById(@PathVariable long id) {
    heroService.findById(id)
  }

  @PostMapping('')
  // only admins can create users
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  Hero save(@RequestBody Hero hero) {
    heroService.save(hero)
  }

  @PutMapping('{id}')
  // only heroes can update their data, unless they are admins
  @PreAuthorize('hasRole("ROLE_ADMIN") or #id == principal.hero.id')
  Hero update(@RequestBody Hero hero, @PathVariable long id) {
    heroService.update(hero, id)
  }

  @DeleteMapping('{id}')
  // only admins can delete users
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  Hero deleteById(@PathVariable long id) {
    heroService.deleteById(id)
  }
}
