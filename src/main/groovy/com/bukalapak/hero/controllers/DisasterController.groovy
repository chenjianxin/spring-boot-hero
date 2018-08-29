package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.Disaster
import com.bukalapak.hero.services.DisasterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('/disaster')
@Transactional
class DisasterController {
  @Autowired
  DisasterService disasterService

  @GetMapping('/')
  List findAll() {
    disasterService.findAll()
  }

  @GetMapping('/{id}')
  Disaster findOne(@PathVariable long id) {
    disasterService.findById(id)
  }

  @PostMapping('/')
  Disaster create(@RequestBody Disaster disaster) {
    disasterService.save(disaster)
  }

  @PutMapping('/{id}')
  Disaster update(@RequestBody Disaster disaster, @PathVariable long id) {
    disasterService.update(disaster, id)
  }

  @DeleteMapping('/{id}')
  Disaster delete(@PathVariable long id) {
    disasterService.deleteById(id)
  }

  @PostMapping('/test')
  Disaster test(@RequestBody Disaster disaster) {
    disaster
  }
}
