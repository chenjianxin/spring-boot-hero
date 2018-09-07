package com.bukalapak.hero.services

import com.bukalapak.hero.models.Hero
import com.bukalapak.hero.models.User
import com.bukalapak.hero.repositories.HeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import javax.persistence.EntityNotFoundException

@Service
class HeroService {
  @Autowired
  HeroRepository heroRepository

  @Autowired
  RoleService roleService

  @Autowired
  PasswordEncoder passwordEncoder

  List findAll() {
    heroRepository.findAll(Sort.by('name')).asList()
  }

  Hero findById(long id) {
    heroRepository.findById(id).orElse(null)
  }

  Hero findByIdOrError(long id) {
    heroRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }

  Hero save(Hero hero) {
    // assign hero to every abilities
    hero.abilities?.each { it.hero = hero }
    if (hero.user == null) { // if user is not supplied, create it
      hero.user = new User(
          username: hero.name.toLowerCase().trim().replaceAll(' ', '-'),
          password: '1234'
      )
    }
    hero.user.password = passwordEncoder.encode(hero.user.password)
    hero.user.hero = hero
    // assign role to hero
    hero.user.authorities = [roleService.findByIdOrError('ROLE_HERO')]
    heroRepository.save(hero)
  }

  Hero update(Hero hero, long id) {
    Hero persisted = findByIdOrError(id)
    persisted.with {
      name = hero.name
    }
    def toBeRemoved = []
    // find values to update & delete
    persisted.abilities.each {
      def a = hero.abilities.find { it2 -> it2.id == it.id }
      if (a == null) toBeRemoved.add(it)
      else it.name = a.name
    }
    persisted.abilities.removeAll(toBeRemoved)
    // assign persisted entity as the hero
    hero.abilities.each {
      if (it.id == null) {
        it.hero = persisted
        persisted.abilities.add(it)
      }
    }

    heroRepository.save(persisted)
  }

  Hero deleteById(long id) {
    def hero = findByIdOrError(id)
    heroRepository.delete(hero)
    hero
  }
}
