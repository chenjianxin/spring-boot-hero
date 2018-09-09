package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.Disaster
import com.bukalapak.hero.services.DisasterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('disaster')
@Transactional
// every user can access, unless specified otherwise
@PreAuthorize('isAuthenticated()')
class DisasterController {
  @Autowired
  DisasterService disasterService

  @GetMapping('')
  List findAll() {
    disasterService.findAll()
  }

  @GetMapping('{id}')
  Disaster findById(@PathVariable long id) {
    disasterService.findById(id)
  }

  @PostMapping('')
  Disaster create(@RequestBody Disaster disaster) {
    disasterService.save(disaster)
  }

  @PutMapping('{id}')
  Disaster update(@RequestBody Disaster disaster, @PathVariable long id) {
    disasterService.update(disaster, id)
  }

  @PostMapping('{id}/hero/{heroId}')
  // only admins and hero themselves can assign hero
  @PreAuthorize('hasRole("ROLE_ADMIN") or #heroId == principal.hero.id')
  Disaster assignHero(@PathVariable long id, @PathVariable long heroId) {
    disasterService.assignHero(id, heroId)
  }

  @DeleteMapping('{id}/hero/{heroId}')
  // only admins and hero themselves can unassign from disaster
  @PreAuthorize('hasRole("ROLE_ADMIN") or #heroId == principal.hero.id')
  Disaster removeHero(@PathVariable long id, @PathVariable long heroId) {
    disasterService.removeHero(id, heroId)
  }

  @PostMapping('{id}')
  // only admins can resolve disasters
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  Disaster resolve(@PathVariable long id) {
    disasterService.resolve(id)
  }

  @DeleteMapping('{id}')
  // onlu admins can delete heroes
  @PreAuthorize('hasRole("ROLE_ADMIN")')
  Disaster deleteById(@PathVariable long id) {
    disasterService.deleteById(id)
  }
}