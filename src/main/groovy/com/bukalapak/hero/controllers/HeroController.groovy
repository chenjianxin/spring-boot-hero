package com.bukalapak.hero.controllers

import com.bukalapak.hero.models.Hero
import com.bukalapak.hero.services.HeroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@RequestMapping('/hero')
@Transactional
class HeroController {
  @Autowired
  HeroService heroService

  @GetMapping('/')
  List findAll() {
    heroService.findAll()
  }

  @GetMapping('/{id}')
  Hero findOne(@PathVariable long id) {
    heroService.findById(id)
  }

  @PostMapping('/')
  Hero save(@RequestBody Hero hero) {
    heroService.save(hero)
  }

  @PutMapping('/{id}')
  Hero update(@RequestBody Hero hero, @PathVariable long id) {
    heroService.update(hero, id)
  }

  @DeleteMapping('/{id}')
  Hero delete(@PathVariable long id) {
    heroService.deleteById(id)
  }
}
