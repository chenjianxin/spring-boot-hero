package com.bukalapak.hero.services

import com.bukalapak.hero.models.Disaster
import com.bukalapak.hero.repositories.DisasterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

import javax.persistence.EntityNotFoundException

@Service // register DisasterService as a Service Spring component
class DisasterService {
  @Autowired // tell Spring to inject value from Spring component
  DisasterRepository disasterRepository

  @Autowired
  HeroService heroService

  List findAll() {
    disasterRepository.findAll(Sort.by(Sort.Order.desc('time'))).asList()
  }

  Disaster findById(long id) {
    disasterRepository.findById(id).orElse(null)
  }

  Disaster findByIdOrError(long id) {
    disasterRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }

  Disaster save(Disaster disaster) {
    disasterRepository.save(disaster)
  }

  Disaster update(Disaster disaster, long id) {
    def persisted = findByIdOrError(id)
    // update entity's values
    persisted.with {
      title = disaster.title
      location = disaster.location
      time = disaster.time
    }
    disasterRepository.save(persisted)
  }

  Disaster assignHero(long id, long heroId) {
    def disaster = findByIdOrError(id)
    def hero = heroService.findByIdOrError(heroId)

    disaster.heroes.add(hero)
    disasterRepository.save(disaster)
  }

  Disaster removeHero(long id, long heroId) {
    def disaster = findByIdOrError(id)
    def hero = heroService.findByIdOrError(heroId)

    disaster.heroes.remove(hero)
    disasterRepository.save(disaster)
  }

  Disaster resolve(long id) {
    def disaster = findByIdOrError(id)
    disaster.isResolved = true

    disasterRepository.save(disaster)
  }

  Disaster deleteById(long id) {
    def disaster = findByIdOrError(id)
    disasterRepository.delete(disaster)
    disaster
  }
}
