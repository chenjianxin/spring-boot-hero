package com.bukalapak.hero.repositories

import com.bukalapak.hero.models.Hero
import org.springframework.data.repository.PagingAndSortingRepository

interface HeroRepository extends PagingAndSortingRepository<Hero, Long> {}