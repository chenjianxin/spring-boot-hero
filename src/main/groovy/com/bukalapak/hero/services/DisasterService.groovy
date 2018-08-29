package com.bukalapak.hero.services

import com.bukalapak.hero.models.Disaster
import com.bukalapak.hero.repositories.DisasterRepository
import com.bukalapak.hero.repositories.HeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service // register DisasterService as a Service Spring component
class DisasterService {
  @Autowired // tell Spring to inject value from Spring component
  DisasterRepository disasterRepository

  @Autowired
  HeroRepository heroRepository

  List findAll() {
    disasterRepository.findAll(Sort.by(Sort.Order.desc('time'))).asList()
  }

  Disaster findById(long id) {
    disasterRepository.findById(id).orElse(null)
  }

  Disaster save(Disaster disaster) {
    def heroes = []
    // retrieve every heroes
    disaster.heroes?.each { heroes.push(heroRepository.findById(it.id).orElseThrow()) }
    disaster.heroes = heroes
    disasterRepository.save(disaster)
  }

  Disaster update(Disaster disaster, long id) {
    def persisted = disasterRepository.findById(id).orElseThrow()
    // update entity's values
    persisted.with {
      title = disaster.title
      location = disaster.location
      time = disaster.time
      heroes = disaster.heroes
    }
    disasterRepository.save(persisted)
  }

  Disaster deleteById(long id) {
    def disaster = findById(id)
    disasterRepository.delete(disaster)
    disaster
  }
}
